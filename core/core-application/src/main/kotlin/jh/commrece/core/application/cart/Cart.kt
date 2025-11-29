package jh.commrece.core.application.cart

import jh.commrece.core.application.order.NewOrder
import jh.commrece.core.application.order.NewOrderItem
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType

data class Cart(
    val userId: Long,
    val items: List<CartItem>,
) {
    fun toNewOrder(targetItemIds: Set<Long>): NewOrder {
        if (items.isEmpty()) throw CoreException(ErrorType.INVALID_REQUEST)
        return NewOrder(
            userId = userId,
            items = items.filter { targetItemIds.contains(it.id) }
                .map {
                    NewOrderItem(
                        productId = it.product.id,
                        quantity = it.quantity,
                    )
                },
        )
    }
}
