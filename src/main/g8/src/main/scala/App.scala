object App {
  import unfiltered.websockets._
  import unfiltered.util.Browser
  import scala.collection.mutable.ListBuffer
  
  def main(args: Array[String]) {
    (1 to $socket_connections$).foreach { i =>
      Browser.open("file://%s" format getClass.getResource("client.html").getFile)
    }
    val sockets = new ListBuffer[WebSocket]()
    WebSocketServer("/", $websocket_port$) {
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
  }
}
