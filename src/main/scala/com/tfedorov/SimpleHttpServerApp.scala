package com.tfedorov

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import com.tfedorov.utils.FileUtils

import java.net.InetSocketAddress
import scala.util.Try

object SimpleHttpServerApp extends App {

  private val PORT = 8000
  private val URI = "/MANIFEST.MF"


  private val server = HttpServer.create(new InetSocketAddress(PORT), 0)
  server.createContext(URI, new RootHandler())
  server.setExecutor(null)

  server.start()

  println(s"Server is up on http://localhost:$PORT$URI")
  println("Hit any key to exit...")

  System.in.read()
  server.stop(0)
  println("Bye...")


  class RootHandler extends HttpHandler {

    def handle(t: HttpExchange) {
      //displayPayload(t.getRequestBody)
      sendResponse(t)
    }


    private def sendResponse(t: HttpExchange) {
      val responseText = readResponse()
      t.sendResponseHeaders(200, responseText.length())
      val os = t.getResponseBody
      os.write(responseText.getBytes)
      os.close()
    }


    private def readResponse(): String = {

      val maybeManifest = Try {
        FileUtils.readManifest("assembly").getOrElse("no assembly MANIFEST"))
      }
      if (maybeManifest.isSuccess)
        maybeManifest.get
      else {
        val throwable = maybeManifest.failed.get
        throwable.getClass + "\n\t" + throwable.getStackTrace.mkString("\n\t")
      }
    }
  }

}