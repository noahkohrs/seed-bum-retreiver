import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class ParseException(message: String) : Exception(message)
class MissingDataException(message: String) : ParseException(message)
class NonParseableData(message: String) : ParseException(message)

fun Document.parseRowContentFromName(rowName: String) : String {
    val row = select("td:contains($rowName)").first()
    return row?.nextElementSibling()?.text()?.trim() ?: throw MissingDataException("Could not parse $rowName")
}


/**
 * Parses a string to extract the protein content in grams
 * @param str the string to parse
 * @return the protein content in grams
 * @throws NonParseableData if the string could not be parsed
 */
fun parseQuantity(str: String) : Product.Quantity {
    return when {
        str.contains("g") -> parseGrams(str)
        else -> throw NonParseableData("Could not parse quantity in $str")
    }
}

fun parseGrams(str: String) : Product.Quantity {
    val regex = """\s*([0-9.]+)\s*g""".toRegex()
    val match = regex.find(str) ?: throw NonParseableData("Could not parse grams in $str")
    return Product.Quantity(match.groupValues[1].toDouble(), "g")
}

fun parseHtmlAsADocument(html: String) : Document {
    val decodedHtml = html
        .replace("\\u003C", "<")
        .replace("\\u003E", ">")
        .replace("\\\"", "\"")

    val document: Document = Jsoup.parse(decodedHtml)
    return document
}

