package com.doongjun.commitmon.extension

import org.springframework.security.core.context.SecurityContextHolder

val currentUserId get() = SecurityContextHolder.getContext().authentication?.principal as Long?
