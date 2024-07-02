package org.example


import NonParseableData
import Product
import ProductJson
import ProductSearchJson
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import parseGrams
import parseQuantity
import parseHtmlAsADocument
import parseRowContentFromName
import java.io.File
import java.io.IOException

val cacheFile = File("./cache")
val client = OkHttpClient.Builder()
    .cache(Cache(cacheFile, 10 * 1024 * 1024))
    .build()

fun fetchItem(itemId: String): ProductJson {
    val request = Request.Builder()
        .url("https://courses.monoprix.fr/api/webproductpagews/v5/products/bop?retailerProductId=$itemId")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        return Gson().fromJson(response.body!!.string(), ProductJson::class.java)
    }
}

fun createProduct(json: ProductJson): Product {
    val name = json.product.name
    val price = json.product.price.amount.toDouble()
    val id = json.product.retailerProductId
    val quantity = parseQuantity(json.product.packSizeDescription)
    val fieldsSize = json.bopData.fields.size

    // We don't know where are the nutritional data, so we have to loop through all fields
    for (i in 0 until fieldsSize) {
        val field = json.bopData.fields[i]
        if (field.title == "nutritionalData") {
            val nutritionalInfos = getNutritionalInfosFromHtml(field.content)
            return Product(
                id = id,
                name = name,
                nutritionalInfos = nutritionalInfos,
                price = price,
                quantity = quantity
            )
        }
    }
    throw NonParseableData("Could not find nutritional data for product $name")
}

fun getProduct(itemId: String): Product? {
    try {
        val item = fetchItem(itemId)
        return createProduct(item)
    } catch (e: Exception) {
        println("Error fetching item $itemId")
        println(e)
        return null
    }
}

fun getProductsForTitle(title: String): List<ProductSearchJson.Prod> {
    val request = Request.Builder()
        .url("https://courses.monoprix.fr/api/v5/products/search?offset=0&term=$title")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val json = Gson().fromJson(response.body!!.string(), ProductSearchJson::class.java)
        return json.entities.product.values.toList()
    }
}

fun main() {
    val products = mutableListOf<Product>()
    val startTime = System.currentTimeMillis()
    val category = listOf(
        "Lait",
        "Oeuf",
        "Viande",
        "Poisson",
        "Fruit"
    )
    var totalProducts = 0
    var success = 0
    var failed = 0
    category.forEach {
        val items = getProductsForTitle(it)
        totalProducts += items.size
        items.forEach { item ->
            CoroutineScope(Dispatchers.IO).launch {
                val product = getProduct(item.retailerProductId)
                if (product != null) {
                    success++
                    products.addLast(product)
                } else {
                    failed++
                }
            }
        }
    }

    // Block until success + failed == totalProducts
    while (success + failed < totalProducts) {
        Thread.sleep(1000)
    }

    products.forEach { it.print() }
    println("Total products: $totalProducts")
    println("Success: $success")
    println("Failed: ${totalProducts - success}")
    println("Time taken: ${System.currentTimeMillis() - startTime}ms")
}


fun getNutritionalInfosFromHtml(html: String): Product.NutritionalInfos {
    val document = parseHtmlAsADocument(html)
    val proteins = document.parseRowContentFromName("Protéines")
    val carbohydrates = try {document.parseRowContentFromName("Glucides") } catch (e: NonParseableData) {null}
    val salt = try {document.parseRowContentFromName("Sel")} catch (e: NonParseableData) {null}
    val fats = try {document.parseRowContentFromName("Matières grasses")} catch (e: NonParseableData) {null}

    return Product.NutritionalInfos(
        proteins = parseGrams(proteins).value,
        carbohydrates = carbohydrates?.let {parseGrams(it).value},
        salt = salt?.let {parseGrams(it).value},
        fats = fats?.let {parseGrams(it).value}
    )
}



