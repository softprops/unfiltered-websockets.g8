# Unfiltered Websockets g8 template

a simple [g8](https://github.com/n8han/giter8#readme) template for demoing [unfiltered](https://github.com/unfiltered/unfiltered#readme) [websockets](https://github.com/unfiltered/Unfiltered/tree/master/netty-websockets/#readme)

## template properties

* name: the name of the websocket project (My Websocket Project)
* unfiltered_version: unfiltered library version (0.5.1)
* websocket_port: port to start server on (5678)
* socket_connections: number of browser clients to open on startup (4)

## install

    g8 softprops/unfiltered-websockets
    # fill in template properties
    cd name-you-gave-project && sbt run

2010-2011 Doug Tangren (softprops)
