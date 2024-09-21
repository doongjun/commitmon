package com.doongjun.commitmon.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
class ThymeleafConfig {
    @Bean
    fun svgTemplateEngine(springResourceTemplateResolver: SpringResourceTemplateResolver): SpringTemplateEngine =
        SpringTemplateEngine().apply {
            setTemplateResolver(springResourceTemplateResolver)
        }

    @Bean
    fun springResourceTemplateResolver(): SpringResourceTemplateResolver =
        SpringResourceTemplateResolver().apply {
            prefix = "classpath:/templates/"
            suffix = ".svg"
            templateMode = TemplateMode.TEXT
            characterEncoding = "UTF-8"
            isCacheable = false
        }
}
