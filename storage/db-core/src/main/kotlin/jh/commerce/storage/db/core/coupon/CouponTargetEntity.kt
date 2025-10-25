package jh.commerce.storage.db.core.coupon

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import jh.commrece.core.enums.CouponTargetType

@Entity
@Table(name = "coupon_target")
class CouponTargetEntity(
    val couponId: Long,
    @Enumerated(EnumType.STRING)
    val targetType: CouponTargetType,
    val targetId: Long,
) : BaseEntity()
