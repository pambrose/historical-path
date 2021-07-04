package com.github.pambrose

actual class PingService : IPingService {
    init {
        println("Created PingService")
    }

    override suspend fun ping(message: String): String {
        return MarkdownParser.toHtml(
            """
            # I am a header $cnt
            ## I am a header $cnt
            ### I am a header $cnt  
            *Italics* **bold**
        """
        )
        //return "<h1>Hello world from server!! $cnt</h1><h3>And this as well</h3>"
    }

    companion object {
        var cnt = 0
    }
}
