object WebSocketServer extends App {
    import unfiltered.netty.websockets._
    import unfiltered.util._
    import scala.collection.concurrent
    import unfiltered.response.ResponseString

    val sockets: concurrent.Map[Int, WebSocket] = concurrent.TrieMap()

    def broadcast(msg: String) = sockets.values.foreach { s =>
        if(s.channel.isActive) s.send(msg)
    }

    unfiltered.netty.Http($websocket_port$).handler(unfiltered.netty.websockets.Planify{
        case _ => {
            case Open(s) =>
                broadcast("%s|joined" format s.channel.hashCode)
                sockets += (s.channel.hashCode -> s)
                s.send("sys|hola!")
            case Message(s, Text(msg)) =>
                broadcast("%s|%s" format(s.channel.hashCode, msg))
            case Close(s) =>
                sockets -= s.channel.hashCode
                broadcast("%s|left" format s.channel.hashCode)
            case Error(s, e) =>
                e.printStackTrace
        }
    })
    .handler(unfiltered.netty.cycle.Planify{
        case _ => ResponseString("not a websocket")
    })
    .run { _ =>
        (1 to $socket_connections$).foreach { _ =>
            val indexFile = getClass.getResource("client.html").getFile
            Browser.open("file://" + indexFile)
        }
    }
}
