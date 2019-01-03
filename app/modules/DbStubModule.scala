package modules

import com.google.inject.{AbstractModule, Scopes}
import repositories.{ItemRepository, ItemRepositoryImpl}

class DbStubModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ItemRepository]).to(classOf[ItemRepositoryImpl]).in(Scopes.SINGLETON)
  }
}
