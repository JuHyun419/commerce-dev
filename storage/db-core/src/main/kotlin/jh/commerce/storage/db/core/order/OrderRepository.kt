package jh.commerce.storage.db.core.order

import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.enums.OrderState
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, Long> {
    fun findByOrderKeyAndStateAndStatus(orderKey: String, state: OrderState, status: EntityStatus): OrderEntity?
    fun findByUserIdAndStateAndStatusOrderByIdDesc(userId: Long, state: OrderState, status: EntityStatus): List<OrderEntity>
}
