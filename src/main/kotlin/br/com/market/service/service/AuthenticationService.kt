package br.com.market.service.service

import br.com.market.service.dto.auth.AuthenticationRequestDTO
import br.com.market.service.dto.auth.RegisterRequestDTO
import br.com.market.service.models.User
import br.com.market.service.repository.UserRepository
import br.com.market.service.response.AuthenticationResponse
import br.com.market.service.security.config.JWTService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(registerRequest: RegisterRequestDTO): AuthenticationResponse? {
        val user = User(
            name = registerRequest.name,
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password)
        )

        userRepository.save(user)
        val token = jwtService.generateToken(user)

        return AuthenticationResponse(token)
    }

    fun authenticate(authenticateRequest: AuthenticationRequestDTO): AuthenticationResponse? {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticateRequest.email, authenticateRequest.password))
        val user = userRepository.findByEmail(authenticateRequest.email).orElseThrow { UsernameNotFoundException("Usuário não encontrado") }
        val token = jwtService.generateToken(user)

        return AuthenticationResponse(token)
    }
}