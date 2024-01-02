package br.com.market.service.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
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
//        http
//            .csrf().disable()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/user/register").permitAll()
//            .requestMatchers("/api/v1/user/registerAll").permitAll()
//            .requestMatchers("/api/v1/user/authenticate").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/v1/viacep/cep").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/v1/client/cpf").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/v1/client/email").permitAll()
//            .requestMatchers(HttpMethod.POST, "/api/v1/client").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/v1/company/{deviceId}").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/v1/market/{deviceId}").permitAll()
//            .requestMatchers(HttpMethod.GET, "/api/v1/device/{id}").permitAll()
//            .requestMatchers("/api/v1/user/sync").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.authorizeHttpRequests { config ->
            config.requestMatchers("/api/v1/user/register").permitAll()
            config.requestMatchers("/api/v1/user/registerAll").permitAll()
            config.requestMatchers("/api/v1/user/authenticate").permitAll()
            config.requestMatchers(HttpMethod.GET, "/api/v1/viacep/cep").permitAll()
            config.requestMatchers(HttpMethod.GET, "/api/v1/client/cpf").permitAll()
            config.requestMatchers(HttpMethod.GET, "/api/v1/client/email").permitAll()
            config.requestMatchers(HttpMethod.POST, "/api/v1/client").permitAll()
            config.requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll()
            config.requestMatchers(HttpMethod.GET, "/api/v1/company/{deviceId}").permitAll()
            config.requestMatchers(HttpMethod.GET, "/api/v1/market/{deviceId}").permitAll()
            config.requestMatchers(HttpMethod.GET, "/api/v1/device/{id}").permitAll()
            config.requestMatchers("/api/v1/user/sync").permitAll()
            config.anyRequest().authenticated()
        }.httpBasic(Customizer.withDefaults())
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .csrf(CsrfConfigurer<HttpSecurity>::disable)

        return http.build()
    }
}