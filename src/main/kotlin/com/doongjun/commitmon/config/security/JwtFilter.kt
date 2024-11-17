package com.doongjun.commitmon.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {
    private val repository = RequestAttributeSecurityContextRepository()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = resolveToken(request)

        if (!token.isNullOrEmpty() && tokenProvider.validateAccessToken(token)) {
            val userId = tokenProvider.extractUserId(token)

            val authenticationToken =
                UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                )

            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
            repository.saveContext(SecurityContextHolder.getContext(), request, response)
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (!authorization.isNullOrEmpty() && authorization.startsWith("Bearer ")) {
            return authorization.substring(7)
        }

        return null
    }
}
