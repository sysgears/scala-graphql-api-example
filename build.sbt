import sbt.Artifact

name := "GraphQL_API_example"
 
version := "1.0" 
      
lazy val `graphql_api_example` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Atlassian Maven Repository" at "https://maven.atlassian.com/content/repositories/atlassian-public/"
      
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  guice,
  "org.sangria-graphql" %% "sangria-play-json" % "1.0.4",
  "org.sangria-graphql" %% "sangria" % "1.4.1",

  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "io.spray" %% "spray-json" % "1.3.5"
)

      