package jh.commrece.core.application.order

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
class OrderItemEntity(
    val orderId: Long,
    val productId: Long,
    val productName: String,
    val thumbnailUrl: String,
    val shortDescription: String,
    val quantity: Long,
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal,
) : BaseEntity()
