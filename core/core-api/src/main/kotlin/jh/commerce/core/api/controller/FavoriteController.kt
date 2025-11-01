package jh.commerce.core.api.controller

import jh.commerce.core.api.controller.request.ApplyFavoriteRequest
import jh.commerce.core.api.controller.request.ApplyFavoriteRequestType
import jh.commerce.core.api.controller.response.FavoriteResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commerce.core.support.response.PageResponse
import jh.commrece.core.application.favorite.FavoriteService
import jh.commrece.core.application.user.User
import jh.commrece.core.support.page.OffsetLimit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1")
class FavoriteController(
    private val favoriteService: FavoriteService,
) {
    @GetMapping("/favorites")
    fun getFavorites(
        user: User,
        @RequestParam offset: Int,
        @RequestParam limit: Int,
    ): ApiResponse<PageResponse<FavoriteResponse>> {
        val page = favoriteService.findFavorites(user, OffsetLimit(offset, limit))

        return ApiResponse.success(PageResponse(FavoriteResponse.of(page.content), page.hasNext))
    }

    @PostMapping("/favorites")
    fun applyFavorite(
        user: User,
        @RequestBody request: ApplyFavoriteRequest,
    ): ApiResponse<Any> {
        when (request.type) {
            ApplyFavoriteRequestType.FAVORITE -> favoriteService.addFavorite(user, request.productId)
            ApplyFavoriteRequestType.UNFAVORITE -> favoriteService.removeFavorite(user, request.productId)
        }

        return ApiResponse.success()
    }
}
