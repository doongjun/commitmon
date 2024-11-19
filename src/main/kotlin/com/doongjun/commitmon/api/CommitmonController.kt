package com.doongjun.commitmon.api

import com.doongjun.commitmon.api.data.CommitmonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/commitmons")
class CommitmonController {
    @GetMapping
    fun getAll(): CommitmonResponse = CommitmonResponse.of()
}
