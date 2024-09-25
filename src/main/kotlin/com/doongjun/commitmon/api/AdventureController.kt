package com.doongjun.commitmon.api

import com.doongjun.commitmon.app.AdventureFacade
import com.doongjun.commitmon.app.Theme
import com.doongjun.commitmon.extension.findBy
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
    ): String =
        adventureFacade.getAnimation(
            username = username,
            theme = Theme::assetName.findBy(theme),
        )
}
