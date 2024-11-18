package com.doongjun.commitmon.config.security

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID

@RedisHash(value = "refreshToken")
data class RefreshToken(
    @Id
    var token: String? = UUID.randomUUID().toString(),
    @TimeToLive
    val ttl: Long = 1209600,
    val userId: Long?,
) {
    fun isLessThanWeek(): Boolean = ttl < 604800
}
