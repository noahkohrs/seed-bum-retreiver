class ProductSearchJson(
    val entities: Result
) {
    data class Result(
        val product : Map<String, Prod>
    )

    data class Prod(
        val productId: String,
        val retailerProductId: String,
        val name: String,
        val brand: String
    )
}
