package modules

import com.google.inject.{AbstractModule, Scopes}
import repositories.{ItemRepository, ItemRepositoryImpl}

/**
  * Module provides dependency injection for ItemRepository trait.
  */
class DbStubModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ItemRepository]).to(classOf[ItemRepositoryImpl]).in(Scopes.SINGLETON)
  }
}
