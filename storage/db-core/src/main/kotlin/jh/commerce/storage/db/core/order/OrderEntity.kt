package jh.commerce.storage.db.core.order

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Index
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import jh.commrece.core.enums.OrderState
import java.math.BigDecimal

@Entity
@Table(
    name = "`orders`",
    indexes = [
        Index(name = "udx_order_key", columnList = "orderKey", unique = true),
    ],
)
class OrderEntity(
    val userId: Long,
    val orderKey: String,
    val name: String,
    val totalPrice: BigDecimal,
    state: OrderState,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var state: OrderState = state
        protected set

    fun paid() {
        state = OrderState.PAID
    }

    fun canceled() {
        state = OrderState.CANCELED
    }
}
