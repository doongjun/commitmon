package com.doongjun.commitmon.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun constructorTest() {
        // given
        val githubId = 1L
        val name = "doongjun"

        // when
        val user = User(githubId, name)

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.githubId).isEqualTo(githubId)
        assertThat(user.name).isEqualTo(name)
        assertThat(user.createdDate).isNotNull()
        assertThat(user.lastModifiedDate).isNotNull()
    }
}
