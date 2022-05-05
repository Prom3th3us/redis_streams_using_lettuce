ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "RedisJavaLettuce"
  )

// https://mvnrepository.com/artifact/io.lettuce/lettuce-core
libraryDependencies += "io.lettuce" % "lettuce-core" % "6.1.8.RELEASE"
enablePlugins(JavaAppPackaging, DockerPlugin)

dockerUsername := sys.props.get("docker.username")
dockerRepository := sys.props.get("docker.registry")

Docker / version := "latest"
Docker / packageName := "lettuce_redis_stream_publisher"
