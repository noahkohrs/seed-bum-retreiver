data class Product(
    val id : String,
    val name : String,
    val nutritionalInfos: NutritionalInfos,
    val price : Double,
    val quantity: Quantity
) {
    data class NutritionalInfos(
        val proteins: Double,
        val carbohydrates: Double? = null,
        val salt: Double? = null,
        val fats : Double? = null,
    ) {
        fun print() {
            println("Proteins: $proteins g")
            carbohydrates?.let { println("Carbohydrates: $it g") }
            salt?.let { println("Salt: $it g") }
            fats?.let { println("Fats: $it g") }
        }
    }

    data class Quantity(
        val value: Double,
        val unit: String
    ) {
        fun print() {
            println("Quantity: $value $unit")
        }
    }


    fun print() {
        println()
        println("Product: $name")
        quantity.print()
        nutritionalInfos.print()
        println("Price: $price")
        println(getProtPerEuro())
        printLinksOfProduct(id)
    }

    fun getProtPerEuro(): Double {
        val priceFor100g = (price / quantity.value) * 100
        return nutritionalInfos.proteins / priceFor100g
    }
}