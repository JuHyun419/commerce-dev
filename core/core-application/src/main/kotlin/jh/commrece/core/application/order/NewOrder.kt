package jh.commrece.core.application.order

data class NewOrder(
    val userId: Long,
    val items: List<NewOrderItem>,
)
