import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

/**
 * Configure all custom components using Guice DI.
 */
class Module extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    // We don't have any class to bind (yet).
  }

}
