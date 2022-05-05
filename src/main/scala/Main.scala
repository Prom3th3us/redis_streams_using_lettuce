import io.lettuce.core.cluster._
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands

import java.util
import scala.util.{Random, Try}

object Main extends App {

  def connectToRedis = {

    import io.lettuce.core.cluster.RedisClusterClient
    import io.lettuce.core.api.StatefulRedisConnection
    import io.lettuce.core.api.sync.RedisCommands

    val redisClient = RedisClusterClient.create(
      s"redis://${Try(sys.env("REDIS_HOST")).getOrElse("redis-redis-cluster")}:6379"
    )
    redisClient
  }

  val message = Try(sys.env("MESSAGE"))
    .getOrElse("hello from Miguel using Java and Lettuce and Redis Streams")
  def createMessageBody = {
    val messageBody: java.util.Map[String, String] =
      new util.HashMap[String, String]
    messageBody.put("message", message)
    messageBody.put("sensor_ts", String.valueOf(System.currentTimeMillis))
    messageBody
  }

  //   Lines 12-14 call the syncCommands.xadd() method using the streams key “weather_sensor:wind” and the message body itself. This method returns the message ID.
  def addToStream(messageBody: java.util.Map[String, String]) = {

    val messageId =
      syncCommands.xadd(
        "key" + Random.nextInt(),
        messageBody
      )
    messageId
  }

  def printMessageIdAndContent = {
    System.out.println(
      String.format("Message %s posted", messageBody)
    )
  }

  def closeResources = {
    connection.close()
    redisClient.shutdown()
  }

  import io.lettuce.core.api.StatefulRedisConnection
  import io.lettuce.core.api.sync.RedisCommands

  val redisClient = connectToRedis

  lazy val connection = redisClient.connect
  lazy val syncCommands = connection.sync

  val messageBody = createMessageBody
  (1 to {
    Try(sys.env("MESSAGES_AMOUNT").toInt).getOrElse(1000 * 1000)
  }) foreach { _ =>
    printMessageIdAndContent
    addToStream(messageBody)
  }

  closeResources

}
