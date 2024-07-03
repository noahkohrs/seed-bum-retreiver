import java.io.File
import java.util.*

class Logger(private val directoryPath: String) {
    private val startTime = System.currentTimeMillis()
    private val fails: MutableMap<String, MutableList<Log>> = mutableMapOf()
    private val success = mutableListOf<Log>()

    data class Log(
        val productId : String,
        val message: String
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
        fails.getOrPut(e.javaClass.simpleName) { mutableListOf() }.add(log)
    }
    fun log(s: String, productId: String) {
        val log = Log(productId, s)
        success.add(log)
    }

    private fun getFile(): File {
        val cal = Calendar.getInstance(Locale.FRANCE)
        val nameId = """
            ${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}-${cal.get(Calendar.HOUR_OF_DAY)}-${cal.get(Calendar.MINUTE)}-${cal.get(Calendar.SECOND)}
        """.trimIndent().replace("\n", "")
        return File(directoryPath, "$nameId.md")
    }

    fun saveLogs() {

        val file = getFile()
        file.appendText("# **Logs of ${ Date(System.currentTimeMillis()) }** \n\n-------------------\n\n")

        file.appendText("## Quick Report\n\n")
        file.appendText(quickResult())

        file.appendText("## Report\n\n")
        fails.keys.forEach {
            file.appendText(categoryText(it))
        }
    }

    fun quickResult(): String {
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        val strBuilder = StringBuilder()
        strBuilder.append("Total time: ${"%.2f".format(duration.toDouble() / 1000)} seconds\n\n")
        strBuilder.append("Total products: ${success.size + fails.values.sumOf { it.size }}\n\n")
        strBuilder.append("Success: ${success.size}\n\n")
        strBuilder.append("Failed attempts: ${fails.values.sumOf { it.size }}\n\n")
        return strBuilder.toString()
    }

    fun categoryText(name: String): String {
        val strBuilder = StringBuilder()
        strBuilder.append("### $name\n\n")
        fails[name]?.forEach {
            strBuilder.append(it.log())
            strBuilder.append("\n\n")
        }
        return strBuilder.toString()
    }

}


