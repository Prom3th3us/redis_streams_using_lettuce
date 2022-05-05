import com.typesafe.sbt.packager.docker.DockerApiVersion

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
Docker / organization := "miguelemosreverte"
Docker / dockerBaseImage := "openjdk"
Docker / packageArchitecture := ""
Docker / packageName := "lettuce_redis_stream_publisher"

/*




apiVersion: batch/v1
kind: Job
metadata:
  name: redis-producer-using-java-one-million-messages
spec:
  template:
    # This is the pod template
    spec:
      containers:
      - name: redis-producer-using-java-one-million-messages
       image: miguelemos/lettuce_redis_stream_publisher:latest
       imagePullPolicy: IfNotPresent
       env:
        - name: REDIS_HOST
         value: "redis-redis-cluster"
        - name: MESSAGES_AMOUNT
         value: "100000000"
        - name: MESSAGE
         value: "hola desde la meeting con Nacho y Miguel"
      restartPolicy: Never
    # The pod template ends here


 */
