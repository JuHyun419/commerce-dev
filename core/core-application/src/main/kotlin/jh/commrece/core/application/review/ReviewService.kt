package jh.commrece.core.application.review

import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewQuery: ReviewQuery,
) {
    fun findRateSummary(target: ReviewTarget): RateSummary {
        return reviewQuery.findRateSummary(target)
    }
}
