package jh.commerce.core.api.controller.request

import jh.commrece.core.application.order.NewOrder
import jh.commrece.core.application.order.NewOrderItem
import jh.commrece.core.application.user.User
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType

data class CreateOrderRequest(
    val productId: Long,
    val quantity: Long,
) {
    fun toNewOrder(user: User): NewOrder {
        if (quantity <= 0) throw CoreException(ErrorType.INVALID_REQUEST)
        return NewOrder(
            userId = user.id,
            items = listOf(NewOrderItem(productId, quantity)),
        )
    }
}
