import java.io.File
import java.util.*

class Logger(private val directoryPath: String) {
    private lateinit var file: File


    private val startTime = System.currentTimeMillis()
    private val content: MutableMap<String, MutableList<Log>> = mutableMapOf()

    data class Log(
        val productId : String,
        val message: String,
        val success: Boolean = false
    ) {
        fun log() : String {
            return """
                **$productId**
                
                _${message}_
                
                - [Web page](https://courses.monoprix.fr/products/$productId/details)
                
                - [JSON](https://courses.monoprix.fr/api/webproductpagews/v5/products/bop?retailerProductId=${productId})
                

            """.trimIndent()
        }
    }

    fun log(e: Exception, productId: String) {
        val log = Log(productId, e.message ?: "No message")
        // println("Missing data for product $productId: ${e.message}")
        content.getOrPut(e.javaClass.simpleName) { mutableListOf() }.add(log)
    }
    fun log(s: String, productId: String) {
        // val log = Log(productId, "", true)
        // println("Missing data for product $productId: ${e.message}")
        // content.getOrPut(s) { mutableListOf() }.add(log)
    }

    fun saveLogs() {
        val cal = Calendar.getInstance(Locale.FRANCE)
        val beginning = "# **LOGS OF ${ Date(System.currentTimeMillis()) }** \n\n-------------------\n\n"
        val nameId = """
            ${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}-${cal.get(Calendar.HOUR_OF_DAY)}-${cal.get(Calendar.MINUTE)}-${cal.get(Calendar.SECOND)}
        """.trimIndent().replace("\n", "")
        file = File(directoryPath, "$nameId.md")
        file.appendText(beginning)
        content.keys.forEach {
            file.appendText(categoryText(it))
        }
    }

    fun categoryText(name: String): String {
        val strBuilder = StringBuilder()
        strBuilder.append("## $name\n\n")
        content[name]?.forEach {
            strBuilder.append(it.log())
            strBuilder.append("\n\n")
        }
        return strBuilder.toString()
    }

}


