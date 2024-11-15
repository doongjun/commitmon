package com.doongjun.commitmon.app

import org.springframework.stereotype.Component

@Component
class AccountFacade(
    private val userService: UserService,
    private val githubOAuth2Service: GithubOAuth2Service,
) {
    fun authenticate(code: String) {
        val userLogin = githubOAuth2Service.getUserLogin(code)
    }
}
