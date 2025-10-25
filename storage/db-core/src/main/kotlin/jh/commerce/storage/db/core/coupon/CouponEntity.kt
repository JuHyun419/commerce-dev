package jh.commerce.storage.db.core.coupon

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import jh.commrece.core.enums.CouponType
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "coupons")
class CouponEntity(
    val name: String,
    @Enumerated(EnumType.STRING)
    val type: CouponType,
    val discount: BigDecimal,
    val expiredAt: LocalDateTime,
) : BaseEntity()
