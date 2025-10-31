package jh.commerce.storage.db.core.order

import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.enums.OrderState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface OrderItemRepository : JpaRepository<OrderItemEntity, Long> {
    fun findByOrderId(orderId: Long): List<OrderItemEntity>
    fun findByOrderIdIn(orderId: Collection<Long>): List<OrderItemEntity>

    @Query(
        """
        SELECT item FROM OrderItemEntity item
            JOIN OrderEntity orderEntity ON item.orderId = orderEntity.id
        WHERE orderEntity.userId = :userId
            AND orderEntity.state = :state
            AND orderEntity.status = :status
            AND orderEntity.createdAt >= :fromDate
            AND item.productId = :productId
            AND item.status = :status
        """,
    )
    fun findRecentOrderItemsForProduct(
        userId: Long,
        productId: Long,
        state: OrderState,
        fromDate: LocalDateTime,
        status: EntityStatus,
    ): List<OrderItemEntity>
}
