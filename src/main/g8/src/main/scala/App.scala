object App {
  import unfiltered.netty.websockets._
  import unfiltered.util._
  import scala.collection.mutable.ConcurrentMap
  import unfiltered.response.ResponseString

  def main(args: Array[String]) {
    import scala.collection.JavaConversions._
    val sockets: ConcurrentMap[Int, WebSocket] =
      new java.util.concurrent.ConcurrentHashMap[Int, WebSocket]

    def notify(msg: String) = sockets.values.foreach { s =>
      if(s.channel.isConnected) s.send(msg)
    }

    unfiltered.netty.Http($websocket_port$).handler(unfiltered.netty.websockets.Planify({
      case _ => {
        case Open(s) =>
          notify("%s|joined" format s.channel.getId)
          sockets += (s.channel.getId.intValue -> s)
          s.send("sys|hola!")
        case Message(s, Text(msg)) =>
          notify("%s|%s" format(s.channel.getId, msg))
        case Close(s) =>
          sockets -= s.channel.getId.intValue
          notify("%s|left" format s.channel.getId)
        case Error(s, e) =>
          e.printStackTrace
      }
    })
    .onPass(_.sendUpstream(_)))
    .handler(unfiltered.netty.cycle.Planify{
      case _ => ResponseString("not a websocket")
    })
    .run {s =>
       (1 to $socket_connections$).foreach { i =>
         Browser.open("file://%s" format(App.getClass.getResource("client.html").getFile))
       }
     }
  }
}
