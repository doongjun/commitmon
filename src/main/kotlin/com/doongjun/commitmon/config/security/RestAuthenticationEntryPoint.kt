package com.doongjun.commitmon.config.security

import com.doongjun.commitmon.core.error.response.ErrorCode
import com.doongjun.commitmon.core.error.response.ErrorResponse
import com.doongjun.commitmon.extension.convertToString
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: AuthenticationException,
    ) {
        val errorResponse = ErrorResponse.of(ErrorCode.UNAUTHORIZED)
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer?.write(errorResponse.convertToString())
    }
}
