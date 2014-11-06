# Unfiltered Websockets g8 template [![Build Status](https://travis-ci.org/softprops/unfiltered-websockets.g8.svg?branch=master)](https://travis-ci.org/softprops/unfiltered-websockets.g8)

A simple [g8][g8] template demoing [unfiltered][uf] [websockets][ws]

## template properties

* name: the name of the websocket project (Unfiltered WebSockets)
* unfiltered_version: unfiltered library version (latest)
* websocket_port: port to start server on (5678)
* socket_connections: number of browser clients to open on startup (4)

## install

    g8 softprops/unfiltered-websockets
    # fill in template properties
    cd name-you-gave-project && sbt run

2010-2012 Doug Tangren (softprops)

[g8]: https://github.com/n8han/giter8#readme
[uf]: http://unfiltered.databinder.net/Unfiltered.html
[ws]: https://github.com/unfiltered/Unfiltered/tree/master/netty-websockets/#readme
