package com.rys.springrysakovbohdan.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class CustomSecurityConfiguration {

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
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
