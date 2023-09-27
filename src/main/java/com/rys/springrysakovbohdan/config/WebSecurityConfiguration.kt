package com.rys.springrysakovbohdan.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(private val userDetailsService: UserDetailsService) {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun customAuthenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers("/").permitAll()
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
