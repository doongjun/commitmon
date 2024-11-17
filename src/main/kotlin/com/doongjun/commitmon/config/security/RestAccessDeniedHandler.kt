package com.doongjun.commitmon.config.security

import com.doongjun.commitmon.core.error.response.ErrorCode
import com.doongjun.commitmon.core.error.response.ErrorResponse
import com.doongjun.commitmon.extension.convertToString
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class RestAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: AccessDeniedException,
    ) {
        val errorResponse = ErrorResponse.of(ErrorCode.ACCESS_DENIED)
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer?.write(errorResponse.convertToString())
    }
}
