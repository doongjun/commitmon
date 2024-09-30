package com.doongjun.commitmon.api

import com.doongjun.commitmon.app.AdventureFacade
import com.doongjun.commitmon.app.Theme
import com.doongjun.commitmon.extension.findBy
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AdventureController(
    private val adventureFacade: AdventureFacade,
) {
    @GetMapping("/adventure", produces = ["image/svg+xml"])
    fun getAdventure(
        @RequestParam username: String,
        @RequestParam(required = false) theme: String?,
        response: HttpServletResponse,
    ): String {
        response.apply {
            setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate, max-age=3600")
            setHeader(HttpHeaders.PRAGMA, "no-cache")
        }
        return adventureFacade.getAnimation(
            username = username,
            theme = Theme::assetName.findBy(theme),
        )
    }
}
