package com.doongjun.commitmon.core.error.response

import org.springframework.validation.BindingResult
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

data class ErrorResponse private constructor(
    val message: String,
    val status: Int,
    val code: String,
    val errors: List<FieldError>,
) {
    companion object {
        fun of(errorCode: ErrorCode) = ErrorResponse(errorCode)

        fun of(
            errorCode: ErrorCode,
            bindingResult: BindingResult,
        ) = ErrorResponse(errorCode, FieldError.of(bindingResult))

        fun of(
            errorCode: ErrorCode,
            errors: List<FieldError>,
        ) = ErrorResponse(errorCode, errors)

        fun of(e: MethodArgumentTypeMismatchException) =
            ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, FieldError.of(e.name, e.value.toString(), e.errorCode))

        fun of(e: MissingServletRequestParameterException) =
            ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, FieldError.of(e.parameterName, null, e.message))
    }

    private constructor(errorCode: ErrorCode, errors: List<FieldError> = emptyList()) : this(
        message = errorCode.message,
        status = errorCode.status,
        code = errorCode.code,
        errors = errors,
    )
}

data class FieldError private constructor(
    val field: String? = "",
    val value: String? = "",
    val reason: String? = "",
) {
    companion object {
        fun of(
            field: String?,
            value: String?,
            reason: String?,
        ) = listOf(FieldError(field, value, reason))

        fun of(bindingResult: BindingResult) =
            bindingResult.fieldErrors.map { error ->
                FieldError(error.field, error.rejectedValue?.toString(), error.defaultMessage)
            }
    }
}
