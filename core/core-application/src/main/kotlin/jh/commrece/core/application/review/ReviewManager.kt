package jh.commrece.core.application.review

import jh.commerce.storage.db.core.review.ReviewEntity
import jh.commerce.storage.db.core.review.ReviewRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * 실제 DB의 CRUD 역할을 하는 Manager 클래스를 더 추가하여 복잡성이 높아지진 않을까?
 */
@Component
class ReviewManager(
    private val reviewRepository: ReviewRepository,
) {
    fun add(reviewKey: ReviewKey, target: ReviewTarget, content: ReviewContent): Long {
        val savedReview = reviewRepository.save(
            ReviewEntity(
                userId = reviewKey.user.id,
                reviewKey = reviewKey.key,
                targetType = target.type,
                targetId = target.id,
                rate = content.rate,
                content = content.content,
            )
        )

        return savedReview.id
    }

    @Transactional
    fun update(user: User, reviewId: Long, reviewContent: ReviewContent): ReviewEntity {
        val review =
            reviewRepository.findByIdAndUserId(reviewId, user.id) ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        review.updateContent(reviewContent.rate, reviewContent.content)
        
        return review
    }

    @Transactional
    fun delete(user: User, reviewId: Long): Long {
        val review =
            reviewRepository.findByIdAndUserId(reviewId, user.id) ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        review.delete()

        return review.id
    }
}
