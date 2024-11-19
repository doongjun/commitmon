package com.doongjun.commitmon.core.error.response

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    INVALID_INPUT_VALUE(400, "A001", "Invalid input value."),
    METHOD_NOT_ALLOWED(405, "A002", "Invalid input value."),
    INTERNAL_SERVER_ERROR(500, "A003", "Server Error."),
    NOT_FOUND(404, "A004", "Not Found."),
    INVALID_TYPE_VALUE(400, "A005", "Invalid type value."),
    ACCESS_DENIED(403, "A006", "Access is denied."),
    UNAUTHORIZED(401, "A007", "Unauthorized."),
}
