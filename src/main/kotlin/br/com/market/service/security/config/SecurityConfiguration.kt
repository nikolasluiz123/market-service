package br.com.market.service.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthFilter: JWTAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/v1/user/register").permitAll()
            .requestMatchers("/api/v1/user/authenticate").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/company/{deviceId}").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/market/{deviceId}").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/device/{id}").permitAll()
            .requestMatchers("/api/v1/user/sync").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}