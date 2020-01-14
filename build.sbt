import akka.grpc.sbt.AkkaGrpcPlugin.autoImport.akkaGrpcGeneratedSources
import sbt.Keys.libraryDependencies

/*lazy val root = project.in(file("."))
  .settings(
  name := "scala_ecosphere",
  version := "0.1",
  scalaVersion := "2.13.1",
  libraryDependencies ++= Seq(
      //"com.google.protobuf"%"protobuf-java"%"3.10.0",
      "org.slf4j"%"slf4j-api"%"1.7.29",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
        exclude("org.slf4j","slf4j-api"),
      "com.typesafe.akka" %% "akka-actor" % "2.6.1",
      "com.typesafe.akka" %% "akka-stream" % "2.6.1",
      "com.typesafe.akka" %% "akka-cluster" % "2.6.1",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.1",
      "com.typesafe.akka" %% "akka-stream-typed" % "2.6.1",
      "com.typesafe.akka" %% "akka-cluster-typed" % "2.6.1",
      "com.typesafe.akka" %% "akka-discovery" % "2.5.23"
        exclude("com.typesafe.akka","akka-actor")
        exclude("com.typesafe.akka","akka-stream")
        exclude("com.typesale.akka","akka-cluster"),
      "com.lightbend.akka.management" %% "akka-management" % "1.0.5"
        exclude("com.typesafe.akka","akka-actor")
        exclude("com.typesafe.akka","akka-stream")
        exclude("com.typesale.akka","akka-cluster"),
      "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % "1.0.5"
        exclude("com.typesafe.akka","akka-actor")
        exclude("com.typesafe.akka","akka-stream")
        exclude("com.typesale.akka","akka-cluster")//,
   // "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
       //exclude("com.google.protobuf","protobuf-java")
    ),

    /*PB.protocVersion := "-v3.10.0",

    PB.targets in Compile := Seq(
      scalapb.gen(
        flatPackage = false,
        javaConversions = false,
        grpc= true,
        singleLineToProtoString = false,
        asciiFormatToString = false,
        lenses = true
        //retainSourceCodeInfo = false
        ) -> (sourceManaged in Compile).value
    ),*/
    // This is the default - both client and server
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Client, AkkaGrpc.Server),

      // only client
      //akkaGrpcGeneratedSources := Seq(AkkaGrpc.Client)

      // only server
     // akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server)
    javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.9" % "runtime;test"

)*/
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1"
ThisBuild / organization := "com.test"
lazy val `scala_ecosphere` = project.in(file(".")).aggregate(gRpcServer,gRpcClient)

lazy val gRpcService = project.in(file("grpc-service"))
  .enablePlugins(AkkaGrpcPlugin)
  .enablePlugins(JavaAgent)
  .settings(
    name:= "grpc-service",
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Client, AkkaGrpc.Server),
    javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.9" % "runtime;test"
)

lazy val gRpcServer = project.in(file("grpc-server")).settings(
   name:="grpc-server",
     libraryDependencies ++= Seq(
     //"com.google.protobuf"%"protobuf-java"%"3.10.0",
     "org.slf4j"%"slf4j-api"%"1.7.29",
     "ch.qos.logback" % "logback-classic" % "1.2.3"
       exclude("org.slf4j","slf4j-api"),
     "com.typesafe.akka" %% "akka-actor" % "2.6.1",
     "com.typesafe.akka" %% "akka-stream" % "2.6.1",
     "com.typesafe.akka" %% "akka-cluster" % "2.6.1",
     "com.typesafe.akka" %% "akka-actor-typed" % "2.6.1",
     "com.typesafe.akka" %% "akka-stream-typed" % "2.6.1",
     "com.typesafe.akka" %% "akka-cluster-typed" % "2.6.1",
     "com.typesafe.akka" %% "akka-http"   % "10.1.11"
       /*"com.typesafe.akka" %% "akka-discovery" % "2.5.23"
         exclude("com.typesafe.akka","akka-actor")
         exclude("com.typesafe.akka","akka-stream")
         exclude("com.typesale.akka","akka-cluster"),
       "com.lightbend.akka.management" %% "akka-management" % "1.0.5"
         exclude("com.typesafe.akka","akka-actor")
         exclude("com.typesafe.akka","akka-stream")
         exclude("com.typesale.akka","akka-cluster"),
       "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % "1.0.5"
         exclude("com.typesafe.akka","akka-actor")
         exclude("com.typesafe.akka","akka-stream")
         exclude("com.typesale.akka","akka-cluster")*///,
     // "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
     //exclude("com.google.protobuf","protobuf-java")
   )
).dependsOn(gRpcService)

lazy val gRpcClient = project.in(file("grpc-client"))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(
  name:="grpc-client"
).dependsOn(gRpcService)