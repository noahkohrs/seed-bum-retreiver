/*
{
  "product": {
    "productId": "3c95bf82-e735-4411-85f6-cf2a59e5207f",
    "retailerProductId": "MPX_875909",
    "type": "REGULAR",
    "name": "Monoprix Lait Demi Ecrémé 6x1L",
    "brand": "Monoprix",
    "packSizeDescription": "6 x 1L",
    "price": {
      "amount": "6.39",
      "currency": "EUR"
    },
    "unitPrice": {
      "price": {
        "amount": "1.07",
        "currency": "EUR"
      },
      "unit": "fop.price.per.litre"
    },
    "available": true,
    "timeRestricted": false,
    "isInShoppingList": false,
    "isInCurrentCatalog": true,
    "medicalQuestionnaireRequired": false,
    "alcohol": false,
    "categoryPath": [
      "Boissons et Lait",
      "Lait",
      "Lait UHT demi-écrémé"
    ]
  },
  "bopData": {
    "fields": [
      {
        "title": "nutritionalData",
        "content": "\u003Ch3 style=\"text-align: left;text-transform: uppercase;color: #4CAF50;\"\u003EInformations Nutritionnelles Moyennes\u003C/h3\u003E\u003Ctable style=\"border-collapse: collapse;width: 100%;\"\u003E\u003Ctbody\u003E\u003Ctr\u003E\u003Cth\u003E\u003C/th\u003E\u003Cth style=\"padding-top: 12px;padding-bottom: 12px;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;text-align:right\"\u003Epour 100 ml\u003C/th\u003E\u003C/tr\u003E\u003Ctr\u003E\u003Cth style=\"padding-top: 12px;padding-bottom: 12px;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;text-align:left\"\u003EValeurs Energétique en Kilojoules\u003C/th\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 195 kJ\u003C/td\u003E\u003C/tr\u003E\u003Ctr style=\"background-color: #f2f2f2;\"\u003E\u003Cth style=\"padding-top: 12px;padding-bottom: 12px;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;text-align:left\"\u003EValeurs Energétique en Kilocalories\u003C/th\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 46 kcal\u003C/td\u003E\u003C/tr\u003E\u003Ctr\u003E\u003Cth style=\"padding-top: 12px;padding-bottom: 12px;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;text-align:left\"\u003EValeurs Nutritionnelles\u003C/th\u003E\u003C/tr\u003E\u003Ctr style=\"background-color: #f2f2f2;\"\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:left\"\u003EMatières grasses\u003C/td\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 1.6 g\u003C/td\u003E\u003C/tr\u003E\u003Ctr\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E\u003Ci\u003EDont Acides Gras Saturés\u003C/i\u003E\u003C/td\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 1.1 g\u003C/td\u003E\u003C/tr\u003E\u003Ctr style=\"background-color: #f2f2f2;\"\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:left\"\u003EGlucides\u003C/td\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 4.7 g\u003C/td\u003E\u003C/tr\u003E\u003Ctr\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E\u003Ci\u003EDont Sucres\u003C/i\u003E\u003C/td\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 4.7 g\u003C/td\u003E\u003C/tr\u003E\u003Ctr style=\"background-color: #f2f2f2;\"\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:left\"\u003EProtéines\u003C/td\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 3.3 g\u003C/td\u003E\u003C/tr\u003E\u003Ctr\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:left\"\u003ESel\u003C/td\u003E\u003Ctd style=\"border: 1px solid #ddd;padding: 8px;text-align:right\"\u003E 0.09 g\u003C/td\u003E\u003C/tr\u003E\u003C/tbody\u003E\u003C/table\u003E"
      }
    ]
  }
}
 */
data class ProductJson(
    val product: Product,
    val bopData: BopData

) {
    data class Price(
        val amount: String,
        val currency: String
    )

    data class UnitPrice(
        val price: Price,
        val unit: String
    )

    data class Product(
        val productId: String,
        val retailerProductId: String,
        val type: String,
        val name: String,
        val brand: String,
        val packSizeDescription: String,
        val price: Price,
        val unitPrice: UnitPrice,
        val available: Boolean,
        val timeRestricted: Boolean,
        val isInShoppingList: Boolean,
        val isInCurrentCatalog: Boolean,
        val medicalQuestionnaireRequired: Boolean,
        val alcohol: Boolean,
        val categoryPath: List<String>
    )

    data class Field(
        val title: String,
        val content: String
    )

    data class BopData(
        val fields: List<Field>
    )
}