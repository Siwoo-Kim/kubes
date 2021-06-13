const http = require("http")
const os = require("os")

console.log("kubia server starting...")

const handler = function (req, res) {
    console.log("Recived req from " + req.connection.remoteAddress)
    res.writeHead(200)
    res.end(`you've hit ${os.hostname()}\n`)
}

const www = http.createServer(handler)
www.listen(8080)