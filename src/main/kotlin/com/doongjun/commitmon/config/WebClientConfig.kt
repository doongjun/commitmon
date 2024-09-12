package com.doongjun.commitmon.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class WebClientConfig {
    @Bean(name = ["githubGraphqlWebClient"])
    fun githubGraphqlWebClient(
        @Value("\${app.github.token}") token: String,
        @Value("\${app.github.base-url}") baseUrl: String,
    ): WebClient =
        WebClient
            .builder()
            .baseUrl("$baseUrl/graphql")
            .defaultHeaders { httpHeaders ->
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }.build()

    @Bean(name = ["githubRestWebClient"])
    fun githubRestWebClient(
        @Value("\${app.github.token}") token: String,
        @Value("\${app.github.base-url}") baseUrl: String,
    ): WebClient =
        WebClient
            .builder()
            .uriBuilderFactory(DefaultUriBuilderFactory(baseUrl))
            .baseUrl(baseUrl)
            .defaultHeaders { httpHeaders ->
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }.build()
}
