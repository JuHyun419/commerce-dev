package jh.commerce.core.api.controller

import jh.commerce.core.api.controller.response.PointResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commrece.core.application.point.PointService
import jh.commrece.core.application.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class PointController(
    private val pointService: PointService
) {
    @GetMapping("/point")
    fun getPoint(user: User): ApiResponse<PointResponse> {
        val balance = pointService.balance(user)
        val histories = pointService.histories(user)

        return ApiResponse.success(PointResponse.of(balance, histories))
    }
}
