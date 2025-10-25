package jh.commerce.core.api.controller.request

import jh.commrece.core.application.review.ReviewContent
import jh.commrece.core.application.review.ReviewTarget
import jh.commrece.core.enums.ReviewTargetType
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import java.math.BigDecimal

data class AddReviewRequest(
    val userId: Long,
    val targetType: ReviewTargetType,
    val targetId: Long,
    val rate: BigDecimal,
    val content: String,
) {
    fun toTarget(): ReviewTarget {
        return ReviewTarget(targetType, targetId)
    }

    fun toContent(): ReviewContent {
        if (rate <= BigDecimal.ZERO) throw CoreException(ErrorType.INVALID_REQUEST)
        if (rate > BigDecimal.valueOf(5.0)) throw CoreException(ErrorType.INVALID_REQUEST)
        if (content.isEmpty()) throw CoreException(ErrorType.INVALID_REQUEST)
        return ReviewContent(rate, content)
    }
}
