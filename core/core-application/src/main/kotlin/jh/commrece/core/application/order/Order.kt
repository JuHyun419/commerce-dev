package jh.commrece.core.application.order

import jh.commrece.core.enums.OrderState
import java.math.BigDecimal

data class Order(
    val id: Long,
    val key: String,
    val name: String,
    val userId: Long,
    val totalPrice: BigDecimal,
    val state: OrderState,
    val items: List<OrderItem>,
)
