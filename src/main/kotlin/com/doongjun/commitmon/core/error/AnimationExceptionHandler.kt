package com.doongjun.commitmon.core.error

import com.doongjun.commitmon.animation.AdventureController
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@RestControllerAdvice(basePackageClasses = [AdventureController::class])
class AnimationExceptionHandler(
    private val svgTemplateEngine: SpringTemplateEngine,
) {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> =
        getErrorSvg("An error occurred while processing the request.").let { svg ->
            ResponseEntity
                .internalServerError()
                .contentType(MediaType("image", "svg+xml"))
                .body(svg)
        }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<String> =
        getErrorSvg(e.message).let { svg ->
            ResponseEntity
                .badRequest()
                .contentType(MediaType("image", "svg+xml"))
                .body(svg)
        }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        e: IllegalArgumentException,
        request: WebRequest,
    ): ResponseEntity<String> =
        getErrorSvg("Could not resolve to a User with the username of '${request.getParameter("username")}'.").let { svg ->
            ResponseEntity
                .badRequest()
                .contentType(MediaType("image", "svg+xml"))
                .body(svg)
        }

    private fun getErrorSvg(errorMessage: String): String =
        svgTemplateEngine
            .process(
                "error",
                Context().apply {
                    setVariable("errorMessage", errorMessage)
                },
            )
}
