package jh.commerce.storage.db.core.point

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import jh.commrece.core.enums.PointType
import java.math.BigDecimal

@Entity
@Table(name = "point_history")
class PointHistoryEntity(
    val userId: Long,
    val type: PointType,
    val referenceId: Long,
    val amount: BigDecimal,
    val balanceAfter: BigDecimal,
) : BaseEntity()
