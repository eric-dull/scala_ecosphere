package com.akka.test

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout
import com.akka.test.grpc.{GreeterService, HelloReply, HelloRequest}

import scala.concurrent.Future
import scala.concurrent.duration._
import akka.pattern._
import akka.stream.{ActorMaterializer, Materializer}
class GreeterServiceActorImpl(system: ActorSystem) extends GreeterService {
  implicit val sys: ActorSystem = system
  implicit val ect = system.dispatcher
  val greeterActor = system.actorOf(GreeterActor.props("Hello"), "greeter")
  implicit val mat: Materializer = ActorMaterializer()
  def sayHello(in: HelloRequest): Future[HelloReply] = {
    // timeout and execution context for ask
    implicit val timeout: Timeout = 3.seconds
    (greeterActor ? GreeterActor.GetGreeting )
      .mapTo[GreeterActor.Greeting]
      .map(message => HelloReply(s"${message.greeting}, ${in.name}"))
  }

  override def itKeepsTalking(in: Source[HelloRequest, NotUsed]): Future[HelloReply] = {
    println(s"sayHello to in stream...")
    in.runWith(Sink.seq).map(elements => HelloReply(s"Hello, ${elements.map(_.name).mkString(", ")}"))
  }

  override def itKeepsReplying(in: HelloRequest): Source[HelloReply, NotUsed] = {
    println(s"sayHello to ${in.name} with stream of chars...")
    Source(s"Hello, ${in.name}".toList).map(character => HelloReply(character.toString))
  }

  override def streamHellos(in: Source[HelloRequest, NotUsed]): Source[HelloReply, NotUsed] = {
    println(s"sayHello to stream...")
    in.map(request => HelloReply(s"Hello, ${request.name}"))
  }
 /* def changeGreeting(in: ChangeRequest): Future[ChangeResponse] = {
    greeterActor ! GreeterActor.ChangeGreeting(in.newGreeting)
    Future.successful(ChangeResponse())
  }*/
}
