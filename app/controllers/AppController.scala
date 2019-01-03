package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.google.inject.{Inject, Singleton}
import graphql.{GraphQL, GraphQLContext, GraphQLContextFactory}
import play.api.Configuration
import play.api.libs.json._
import play.api.mvc._
import sangria.ast.Document
import sangria.execution._
import sangria.marshalling.playJson._
import sangria.parser.QueryParser

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}


/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class AppController @Inject()(cc: ControllerComponents,
                              graphQLContextFactory: GraphQLContextFactory,
                              env: play.Environment,
                              config: Configuration,
                              implicit val executionContext: ExecutionContext) extends AbstractController(cc) {

  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def graphiql = Action(Ok(views.html.graphiql()))

  def graphql(value: String, variable: Option[String], operation: Option[String]) = Action.async {
    request =>
      executeQuery(value, variable.map(parseVariables), operation) {
        graphQLContextFactory.createContextForRequest()
      }
  }

  def graphqlBody = Action.async(parse.json) {
    implicit request: Request[JsValue] =>

      val extract: JsValue => (String, Option[String], Option[JsObject]) = query => (
        (query \ "query").as[String],
        (query \ "operationName").asOpt[String],
        (query \ "variables").toOption.flatMap {
          case JsString(vars) => Some(parseVariables(vars))
          case obj: JsObject => Some(obj)
          case _ ⇒ None
        }
      )

      val maybeQuery: Try[(String, Option[String], Option[JsObject])] = Try {
        request.body match {
          case arrayBody@JsArray(_) => extract(arrayBody.value(0))
          case objectBody@JsObject(_) => extract(objectBody)
          case otherType =>
            throw new Error {
              s"/graphql endpint does not support request body of type [${otherType.getClass.getSimpleName}]"
            }
        }
      }

      maybeQuery match {
        case Success((query, operationName, variables)) => executeQuery(query, variables, operationName) {
          graphQLContextFactory.createContextForRequest()
        }
        case Failure(error) => Future.successful {
          BadRequest(error.getMessage)
        }
      }
  }

  def parseVariables(variables: String): JsObject = if (variables.trim.isEmpty || variables.trim == "null") Json.obj()
  else Json.parse(variables).as[JsObject]

  def executeQuery(query: String, variables: Option[JsObject] = None, operation: Option[String] = None)
                  (graphQLContext: GraphQLContext): Future[Result] = QueryParser.parse(query) match {
    case Success(queryAst: Document) => Executor.execute(
      schema = GraphQL.Schema,
      queryAst = queryAst,
      userContext = graphQLContext,
      variables = variables.getOrElse(Json.obj()),
      exceptionHandler = exceptionHandler,
      queryReducers = List(
        QueryReducer.rejectMaxDepth[GraphQLContext](GraphQL.maxQueryDepth),
        QueryReducer.rejectComplexQueries[GraphQLContext](GraphQL.maxQueryComplexity, (_, _) => TooComplexQueryError)
      )
    ).map(Ok(_)).flatMap {
      result =>
        Future(result)
    }.recover {
      case error: QueryAnalysisError ⇒ BadRequest(error.resolveError)
      case error: ErrorWithResolver ⇒ InternalServerError(error.resolveError)
    }
    case Failure(ex) => Future.successful(Ok(s"${ex.getMessage}"))
  }

  lazy val exceptionHandler = ExceptionHandler {
    case (_, error@TooComplexQueryError) ⇒ HandledException(error.getMessage)
    case (_, error@MaxQueryDepthReachedError(_)) ⇒ HandledException(error.getMessage)
  }

  case object TooComplexQueryError extends Exception("Query is too expensive.")

}
