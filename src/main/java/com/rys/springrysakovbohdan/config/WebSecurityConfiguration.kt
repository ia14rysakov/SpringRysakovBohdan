package com.rys.springrysakovbohdan.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class WebSecurityConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers(
                AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/petition/**"),
                AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/petition/**"),
                AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/petition/**")
            ).authenticated()
            .requestMatchers("/petition/**").permitAll()
            .requestMatchers("/user/**").authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/user/**")).permitAll()
            .requestMatchers("/vote/**").authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic()
        return http.build()
    }
}
