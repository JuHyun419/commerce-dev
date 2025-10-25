package jh.commrece.core.application.review

import jh.commerce.storage.db.core.review.ReviewRepository
import org.springframework.stereotype.Component

@Component
class ReviewQuery(
    private val reviewRepository: ReviewRepository,
) {
    fun findRateSummary(target: ReviewTarget): RateSummary {
        val reviews = reviewRepository.findByTargetTypeAndTargetId(target.type, target.id)
            .filter { it.isActive() }

        return if (reviews.isEmpty()) {
            RateSummary.EMPTY
        } else {
            RateSummary(
                rate = reviews.sumOf { it.rate }.divide(reviews.size.toBigDecimal()),
                count = reviews.size.toLong()
            )
        }
    }
}
