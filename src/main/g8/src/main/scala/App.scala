object App {
  import unfiltered.netty.websockets._
  import unfiltered.util.Browser
  import scala.collection.mutable.ListBuffer
  import unfiltered.response.ResponseString

  def main(args: Array[String]) {
    (1 to $socket_connections$).foreach { i =>
      Browser.open("file://%s" format getClass.getResource("client.html").getFile)
    }
    val sockets = new ListBuffer[WebSocket]()

    unfiltered.netty.Http($websocket_port$).handler(unfiltered.netty.websockets.Planify({
      case _ =>{
          case Open(s) =>
            sockets.foreach(_.send("socket %s joined" format sockets.size + 1))
            sockets + s
            s.send("hola!")
          case Message(s, Text(msg)) =>
            sockets.foreach(_.send(msg))
          case Close(s) =>
            sockets - s
            println("closed socket %s" format(s))
          case Error(s, e) =>
            println("error occured %s" format e.getMessage)
      }
    })
     .onPass(_.sendUpstream(_))
    .handler(unfiltered.netty.cycle.Planify{
      case _ => ResponseString("not a websocket")
    })
    .run
  }
}
