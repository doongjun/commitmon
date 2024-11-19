package com.doongjun.commitmon.api

import com.doongjun.commitmon.api.data.ChangeCommitmonRequest
import com.doongjun.commitmon.api.data.MeDetailResponse
import com.doongjun.commitmon.api.data.MeResponse
import com.doongjun.commitmon.app.UserFetchType
import com.doongjun.commitmon.app.UserService
import com.doongjun.commitmon.extension.currentUserId
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/me")
class MeController(
    private val userService: UserService,
) {
    @Operation(summary = "로그인 유저 조회")
    @GetMapping
    fun get(): MeResponse =
        MeResponse.from(
            user = userService.getSimple(currentUserId!!),
        )

    @Operation(summary = "로그인 유저 조회 (팔로워, 팔로잉 포함)")
    @GetMapping("/detail")
    fun getDetail(): MeDetailResponse =
        MeDetailResponse.from(
            user =
                userService.get(
                    id = currentUserId!!,
                    userFetchType = UserFetchType.ALL,
                ),
        )

    @Operation(summary = "로그인 유저 커밋몬 변경")
    @PostMapping("/commitmon/change")
    fun changeCommitmon(
        @Valid @RequestBody request: ChangeCommitmonRequest,
    ) {
        userService.changeCommitmon(
            id = currentUserId!!,
            commitmon = request.commitmon!!,
        )
    }
}
