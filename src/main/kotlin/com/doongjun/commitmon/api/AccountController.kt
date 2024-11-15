package com.doongjun.commitmon.api

import com.doongjun.commitmon.api.data.RedirectDestination
import com.doongjun.commitmon.app.AccountFacade
import com.doongjun.commitmon.app.GithubOAuth2Service
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val accountFacade: AccountFacade,
    private val githubOAuth2Service: GithubOAuth2Service,
) {
    @GetMapping("/login")
    fun login(
        @RequestHeader("Redirect-Destination", defaultValue = "LOCAL") destination: RedirectDestination,
    ): ResponseEntity<Unit> =
        ResponseEntity
            .status(HttpStatus.MOVED_PERMANENTLY)
            .header(
                HttpHeaders.LOCATION,
                githubOAuth2Service.getRedirectUrl(destination),
            ).build()

    @GetMapping("/oauth/github/callback/{destination}")
    fun loginCallback(
        @PathVariable destination: RedirectDestination,
        @RequestParam code: String,
    ) {
        accountFacade.authenticate(code)
    }
}
