package jh.commerce.core.api.controller.response

import jh.commrece.core.application.favorite.Favorite
import java.time.LocalDateTime
import kotlin.collections.map

data class FavoriteResponse(
    val id: Long,
    val productId: Long,
    val favoritedAt: LocalDateTime,
) {
    companion object {
        fun of(f: Favorite): FavoriteResponse {
            return FavoriteResponse(
                id = f.id,
                productId = f.productId,
                favoritedAt = f.favoritedAt,
            )
        }

        fun of(favorites: List<Favorite>): List<FavoriteResponse> {
            return favorites.map { of(it) }
        }
    }
}
