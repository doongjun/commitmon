package com.doongjun.commitmon.api

import com.doongjun.commitmon.api.data.CommitmonResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/commitmons")
class CommitmonController {
    @Operation(summary = "커밋몬 목록 조회")
    @GetMapping
    fun getAll(): CommitmonResponse = CommitmonResponse.of()
}
