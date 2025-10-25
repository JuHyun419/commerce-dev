package jh.commrece.core.application.review

import jh.commerce.storage.db.core.review.ReviewRepository
import jh.commrece.core.application.order.OrderItemRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.enums.OrderState
import jh.commrece.core.enums.ReviewTargetType
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Validator 컴포넌트에서 xxxRepository 에 의존하는 것이 적절할까?
 */
@Component
class ReviewPolicyValidator(
    private val orderItemRepository: OrderItemRepository,
    private val reviewRepository: ReviewRepository,
) {
    fun validateNew(user: User, target: ReviewTarget): ReviewKey {
        if (target.type != ReviewTargetType.PRODUCT) {
            throw UnsupportedOperationException()
        }

        val reviewKeys = orderItemRepository.findRecentOrderItemsForProduct(
            userId = user.id,
            productId = target.id,
            state = OrderState.PAID,
            fromDate = LocalDateTime.now().minusDays(14),
            status = EntityStatus.ACTIVE,
        )
            .map { "ORDER_ITEM_${it.id}" }

        val existReviewKeys =
            reviewRepository.findByUserIdAndReviewKeyIn(user.id, reviewKeys).map { it.reviewKey }.toSet()

        return ReviewKey(
            user = user,
            key = reviewKeys.firstOrNull { it !in existReviewKeys }
                ?: throw CoreException(ErrorType.REVIEW_HAS_NOT_ORDER),
        )
    }

    fun validateUpdate(user: User, reviewId: Long) {
        val review = reviewRepository.findByIdAndUserId(reviewId, user.id)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        if (review.createdAt.plusDays(7).isBefore(LocalDateTime.now()))
            throw CoreException(ErrorType.REVIEW_UPDATE_EXPIRED)
    }
}
