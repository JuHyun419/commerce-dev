package jh.commerce.core.api.controller.request

data class ApplyFavoriteRequest(
    val productId: Long,
    val type: ApplyFavoriteRequestType,
)

enum class ApplyFavoriteRequestType {
    FAVORITE,
    UNFAVORITE,
}
