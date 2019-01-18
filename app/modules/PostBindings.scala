package modules

import com.google.inject._
import repositories.{PostPostRepositoryImpl, PostRepository}

class PostBindings extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[PostRepository]).to(classOf[PostPostRepositoryImpl]).in(Scopes.SINGLETON)
  }
}