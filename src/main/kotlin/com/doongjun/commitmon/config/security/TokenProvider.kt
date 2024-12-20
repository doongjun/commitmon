package com.doongjun.commitmon.config.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.Date

@Component
class TokenProvider(
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${app.auth.jwt.base64-secret}")
    private val base64Secret: String,
    @Value("\${app.auth.jwt.expired-ms}")
    private val jwtExpiredMs: Long,
    @Value("\${app.auth.refresh-token.expired-ms}")
    private val refreshTokenExpiredMs: Long,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun createAccessToken(userId: Long): String {
        val now = Date()
        val expiration = Date(now.time + jwtExpiredMs)

        return Jwts
            .builder()
            .claims(mapOf("sub" to userId.toString()))
            .issuedAt(now)
            .expiration(expiration)
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)))
            .compact()
    }

    fun extractUserId(accessToken: String): Long {
        val claims =
            Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)))
                .build()
                .parseSignedClaims(accessToken)
                .payload
        return claims["sub"].toString().toLong()
    }

    fun validateAccessToken(accessToken: String): Boolean {
        try {
            Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)))
                .build()
                .parseSignedClaims(accessToken)

            return true
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            log.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            log.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            log.error("JWT claims string is empty.")
        }
        return false
    }

    fun createRefreshToken(userId: Long) =
        refreshTokenRepository
            .save(
                RefreshToken(userId = userId, ttl = refreshTokenExpiredMs / 1000),
            ).token!!

    fun expireRefreshToken(refreshToken: String) = refreshTokenRepository.deleteById(refreshToken)

    fun getRefreshToken(refreshToken: String) = refreshTokenRepository.findByIdOrNull(refreshToken)
}
