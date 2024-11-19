package com.doongjun.commitmon.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()
                    .requestMatchers("/adventure")
                    .permitAll()
                    .requestMatchers("/api/v1/account/**")
                    .permitAll()
                    .requestMatchers("/api/v1/commitmons/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }.exceptionHandling { exception ->
                exception
                    .accessDeniedHandler(RestAccessDeniedHandler())
                    .authenticationEntryPoint(RestAuthenticationEntryPoint())
            }

        return http.build()
    }
}
