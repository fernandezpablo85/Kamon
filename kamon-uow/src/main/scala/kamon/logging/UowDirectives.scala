package kamon.logging

import java.util.concurrent.atomic.AtomicLong
import spray.routing.Directive0
import spray.routing.directives.BasicDirectives
import java.net.InetAddress
import scala.util.Try
import kamon.{Tracer, Kamon}

trait UowDirectives extends BasicDirectives {
  def uow: Directive0 = mapRequest { request =>
    val generatedUow = Some(UowDirectives.newUow)
    println("Generated UOW: "+generatedUow)
    Tracer.set(Tracer.newTraceContext().copy(userContext = generatedUow))


    request
  }
}

object UowDirectives {
  val uowCounter = new AtomicLong

  val hostnamePrefix = Try(InetAddress.getLocalHost.getHostName).getOrElse("unknown-localhost")

  def newUow = "%s-%s".format(hostnamePrefix, uowCounter.incrementAndGet())

}
