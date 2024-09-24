package com.doongjun.commitmon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommitmonApplication

fun main(args: Array<String>) {
    runApplication<CommitmonApplication>(*args)
}
