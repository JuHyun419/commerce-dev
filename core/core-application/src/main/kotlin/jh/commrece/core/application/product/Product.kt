package jh.commrece.core.application.product

data class Product(
    val id: Long,
    val name: String,
    val thumbnailUrl: String,
    val description: String,
    val shortDescription: String,
    val price: Price,
)
