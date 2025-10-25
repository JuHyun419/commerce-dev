package jh.commerce.storage.db.core.review

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Index
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import jh.commrece.core.enums.ReviewTargetType
import java.math.BigDecimal

@Entity
@Table(
    name = "reviews",
    indexes = [
        Index(name = "idx_user_review", columnList = "userId, reviewKey", unique = true),
    ],
)
class ReviewEntity(
    val userId: Long,
    val reviewKey: String,

    @Enumerated(EnumType.STRING)
    val targetType: ReviewTargetType,
    val targetId: Long,
    rate: BigDecimal,
    content: String,
) : BaseEntity() {
    var rate: BigDecimal = rate
        protected set

    @Column(columnDefinition = "TEXT")
    var content: String = content
        protected set
}
