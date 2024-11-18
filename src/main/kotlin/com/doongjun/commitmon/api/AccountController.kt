package com.doongjun.commitmon.api

import com.doongjun.commitmon.api.data.RedirectDestination
import com.doongjun.commitmon.api.data.RefreshTokenRequest
import com.doongjun.commitmon.api.data.RefreshTokenResponse
import com.doongjun.commitmon.app.AccountFacade
import com.doongjun.commitmon.app.GithubOAuth2Service
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountFacade: AccountFacade,
    private val githubOAuth2Service: GithubOAuth2Service,
) {
    @Operation(summary = "로그인")
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

    @Operation(summary = "로그인 Callback (시스템에서 호출)")
    @GetMapping("/oauth/github/callback/{destination}")
    fun loginCallback(
        @PathVariable destination: RedirectDestination,
        @RequestParam code: String,
    ): ResponseEntity<Unit> {
        val (accessToken, refreshToken) = accountFacade.login(code)
        return ResponseEntity
            .status(HttpStatus.TEMPORARY_REDIRECT)
            .header(
                HttpHeaders.LOCATION,
                destination.getClientUrl(accessToken, refreshToken),
            ).build()
    }

    @Operation(summary = "토큰 갱신")
    @PostMapping("/refresh")
    fun refresh(
        @Valid @RequestBody request: RefreshTokenRequest,
    ): ResponseEntity<RefreshTokenResponse> {
        val (accessToken, refreshToken) = accountFacade.refresh(token = request.refreshToken!!)
        return ResponseEntity.ok(RefreshTokenResponse.of(accessToken, refreshToken))
    }
}
