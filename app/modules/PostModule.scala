package modules

import com.google.inject._
import repositories.{PostRepositoryImpl, PostRepository}

class PostModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[PostRepository]).to(classOf[PostRepositoryImpl]).in(Scopes.SINGLETON)
  }
}