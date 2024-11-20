package com.doongjun.commitmon.animation

import com.doongjun.commitmon.app.AdventureFacade
import com.doongjun.commitmon.app.Theme
import com.doongjun.commitmon.app.UserFetchType
import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.extension.currentUserId
import com.doongjun.commitmon.extension.findBy
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AdventureController(
    private val adventureFacade: AdventureFacade,
) {
    @Operation(summary = "유저 애니메이션 SVG")
    @GetMapping("/adventure", produces = ["image/svg+xml"])
    fun getAdventure(
        @RequestParam username: String,
        @RequestParam(required = false) theme: String?,
        @RequestParam(required = false) userFetchType: String?,
        response: HttpServletResponse,
    ): String {
        response.apply {
            setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate, max-age=3600")
            setHeader(HttpHeaders.PRAGMA, "no-cache")
        }
        return adventureFacade.getAnimation(
            username = username,
            theme = Theme::assetName.findBy(theme?.lowercase()),
            userFetchType = UserFetchType::title.findBy(userFetchType?.lowercase()),
        )
    }

    @Operation(summary = "로그인 유저 애니메이션 미리보기 SVG")
    @GetMapping("/adventure/preview", produces = ["image/svg+xml"])
    fun getAdventurePreview(
        @RequestParam(required = false) commitmon: Commitmon?,
        @RequestParam(defaultValue = "GRASSLAND") theme: Theme,
        @RequestParam(defaultValue = "SOLO") userFetchType: UserFetchType,
        response: HttpServletResponse,
    ): String =
        adventureFacade.getAnimationPreview(
            userId = currentUserId!!,
            commitmon = commitmon,
            theme = theme,
            userFetchType = userFetchType,
        )
}
