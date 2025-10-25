package jh.commerce.storage.db.core.review

import jh.commrece.core.enums.ReviewTargetType
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<ReviewEntity, Long> {
    fun findByUserIdAndReviewKeyIn(userId: Long, reviewKey: Collection<String>): List<ReviewEntity>
    fun findByTargetTypeAndTargetId(target: ReviewTargetType, targetId: Long): List<ReviewEntity>
}
