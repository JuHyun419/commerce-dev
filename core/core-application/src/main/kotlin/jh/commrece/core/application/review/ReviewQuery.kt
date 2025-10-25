package jh.commrece.core.application.review

import jh.commerce.storage.db.core.review.ReviewRepository
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.support.page.OffsetLimit
import jh.commrece.core.support.page.Page
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

    fun find(reviewTarget: ReviewTarget, offsetLimit: OffsetLimit): Page<Review> {
        val review = reviewRepository.findByTargetTypeAndTargetIdAndStatus(
            target = reviewTarget.type,
            targetId = reviewTarget.id,
            status = EntityStatus.ACTIVE,
            slice = offsetLimit.toPageable(),
        )

        return Page(
            review.content.map {
                Review(
                    id = it.id,
                    userId = it.userId,
                    target = ReviewTarget(
                        type = it.targetType,
                        id = it.targetId
                    ),
                    content = ReviewContent(
                        rate = it.rate,
                        content = it.content
                    ),
                )
            },
            hasNext = review.hasNext()
        )
    }
}
