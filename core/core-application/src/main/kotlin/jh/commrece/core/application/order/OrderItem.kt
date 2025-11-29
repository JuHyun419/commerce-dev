package jh.commrece.core.application.order

import java.math.BigDecimal

data class OrderItem(
    val orderId: Long,
    val productId: Long,
    val productName: String,
    val thumbnailUrl: String,
    val shortDescription: String,
    val quantity: Long,
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal,
)
