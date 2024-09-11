package com.doongjun.commitmon.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean(name = ["githubGraphqlWebClient"])
    fun githubGraphqlWebClient(
        @Value("\${app.github.token}") token: String,
        @Value("\${app.github.graphql.base-url}") url: String,
    ): WebClient =
        WebClient
            .builder()
            .baseUrl(url)
            .defaultHeaders { httpHeaders ->
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }.build()
}
