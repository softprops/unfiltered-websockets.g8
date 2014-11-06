object App {
  import unfiltered.netty.websockets._
  import unfiltered.util._
  import scala.collection.concurrent.TrieMap
  import unfiltered.response.ResponseString

  def main(args: Array[String]) {
    val sockets: TrieMap[Int, WebSocket] = TrieMap.empty

    def notify(msg: String) = sockets.values.foreach { s =>
      if(s.channel.isActive) s.send(msg)
    }

    unfiltered.netty.Server.local($websocket_port$).handler(unfiltered.netty.websockets.Planify({
      case _ => {
        case Open(s) =>
          notify("%s|joined" format s.channel.##)
          sockets += (s.channel.## -> s)
          s.send("sys|hola!")
        case Message(s, Text(msg)) =>
          notify("%s|%s" format(s.channel.##, msg))
        case Close(s) =>
          sockets -= s.channel.##
          notify("%s|left" format s.channel.##)
        case Error(s, e) =>
          e.printStackTrace
      }
    }))
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
