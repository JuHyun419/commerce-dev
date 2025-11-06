package jh.commrece.core.application.cart

import jh.commrece.core.application.product.Product

data class CartItem(
    val id: Long,
    val product: Product,
    val quantity: Long,
)
