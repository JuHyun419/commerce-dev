package jh.commerce.core.api.controller.request

data class CreateOrderFromCartRequest(
    val cartItemIds: Set<Long>,
)
