package jh.commrece.core.application.favorite

import jakarta.transaction.Transactional
import jh.commerce.storage.db.core.favorite.FavoriteEntity
import jh.commerce.storage.db.core.favorite.FavoriteRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import jh.commrece.core.support.page.OffsetLimit
import jh.commrece.core.support.page.Page
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FavoriteService(
    private val favoriteRepository: FavoriteRepository,
) {
    fun findFavorites(user: User, offsetLimit: OffsetLimit): Page<Favorite> {
        // TODO: 하루에 생성되는 찜 수가 많거나, 과거 데이터를 삭제해야 하는 처리의 케이스 고려
        val cutoff = LocalDateTime.now().minusDays(30)
        val result = favoriteRepository.findByUserIdAndStatusAndUpdatedAtAfter(
            user.id,
            EntityStatus.ACTIVE,
            cutoff,
            offsetLimit.toPageable(),
        )

        return Page(
            result.content.map {
                Favorite(
                    id = it.id,
                    userId = it.userId,
                    productId = it.productId,
                    favoritedAt = it.favoritedAt,
                )
            },
            result.hasNext(),
        )
    }

    @Transactional
    fun addFavorite(user: User, productId: Long): Long {
        val existing = favoriteRepository.findByUserIdAndProductId(user.id, productId)

        return if (existing == null) {
            val saved = favoriteRepository.save(
                FavoriteEntity(
                    userId = user.id,
                    productId = productId,
                    favoritedAt = LocalDateTime.now(),
                ),
            )
            saved.id
        } else {
            existing.favorite()
            existing.id
        }
    }

    @Transactional
    fun removeFavorite(user: User, productId: Long): Long {
        val existing = favoriteRepository.findByUserIdAndProductId(user.id, productId)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)
        existing.delete()

        return existing.id
    }
}
