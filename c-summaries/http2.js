function Http2Session$Receive(arg0) {
    
}

function Http2Stream$GetID() {
    return 0
}

function httpErrorString(arg0) {
    return ""
}

function Http2Session$SetNextStreamID(id) {
    return true
}

function Http2Session$SetLocalWindowSize(windows_size) {
    return {}
}

function Http2Session$New(arg0) {

}

function Http2Session$Consume(arg0) {

}


function Http2Session$Destroy(arg0, arg1) {

}

function Http2Session$Request(arg0, arg1) {

}

function Http2Session$Goaway(arg0, arg1, arg2) {

}

function Http2Session$UpdateChunksSent() {
    return 1
}

function Http2Stream$RstStream(arg0) {

}

function Http2Stream$Respond(arg0, arg1) {
    return -1
}

function Http2Stream$Info(arg0) {
    return -1
}

function Http2Stream$Trailers(arg0) {
    return -1
}

function Http2Stream$PushPromise(arg0, arg1) { return object }

function Http2Stream$Priority(arg0, arg1, arg2, arg3) { }

function Http2Session$AltSvce(arg0, arg1, arg2) { }

function Http2Session$Origin(arg0, arg1) { }

function Http2Session$Ping(arg0, arg1) { }

function HttpHttp2Session$Settings(arg0) { return true }

function SetCallbackFunctions(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10)

Http2Session$New.prototype = {
    origin: TAJS_CFunction(Http2Session$Origin, "string", "int"),
  altsvc: TAJS_CFunction(Http2Session$AltSvce, "int", "string", "string"),
  ping: TAJS_CFunction(Http2Session$Ping, "string", "int"),
  consume: TAJS_CFunction(Http2Session$Consume, "object"),
  receive: TAJS_CFunction(Http2Session$Receive, "object"),
  destroy: TAJS_CFunction(Http2Session$Destroy, "uint", "boolean"),
  goaway: TAJS_CFunction(Http2Session$Goaway, "uint", "int", "array"),
  settings: TAJS_CFunction(HttpHttp2Session$Settings, "function"),
  request: TAJS_CFunction(Http2Session$Request, "array", "int"),
  setNextStreamID: TAJS_CFunction(Http2Session$SetNextStreamID, "int"),
  setLocalWindowSize: TAJS_CFunction(Http2Session$SetLocalWindowSize, "int"),
  updateChunksSent: TAJS_CFunction(Http2Session$UpdateChunksSent),
  refreshState (){},
  localSettings (){},
  remoteSettings (){},
}

function Http2Stream$New() { }
Http2Stream$New.prototype = {
        id: TAJS_CFunction(Http2Stream$GetID),
        destroy () { },
        priority: TAJS_CFunction(Http2Stream$Priority, "int", "int", "boolean", "boolean"),
        pushPromise: TAJS_CFunction(Http2Stream$PushPromise, "array", "int"),
        info: TAJS_CFunction(Http2Stream$Info, "array"),
        trailers: TAJS_CFunction(Http2Stream$Trailers, "array"),
        respond: TAJS_CFunction(Http2Stream$Respond, "array", "int"),
        rstStream: TAJS_CFunction(Http2Stream$RstStream, "uint"),
        refreshState () { },
}

module.exports = {
    Http2Session: TAJS_CFunction(Http2Session$New, "int"),
    Http2Stream: TAJS_CFunction(Http2Stream$New),
    nghttp2ErrorString: TAJS_CFunction(httpErrorString, "uint", "string"),
    setCallbackFunctions: TAJS_CFunction(SetCallbackFunctions, "function", "function", "function", "function", "function", "function", "function", "function", "function", "function", "function"),
}