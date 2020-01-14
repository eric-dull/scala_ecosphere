//addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.27")
//libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.4"
// in project/plugins.sbt:
addSbtPlugin("com.lightbend.akka.grpc" % "sbt-akka-grpc" % "0.7.3")
addSbtPlugin("com.lightbend.sbt" % "sbt-javaagent" % "0.1.4") // ALPN agent