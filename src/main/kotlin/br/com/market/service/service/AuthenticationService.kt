package br.com.market.service.service

import br.com.market.service.dto.auth.AuthenticationRequestDTO
import br.com.market.service.dto.auth.RegisterRequestDTO
import br.com.market.service.models.User
import br.com.market.service.repository.UserRepository
import br.com.market.service.response.AuthenticationResponse
import br.com.market.service.security.config.JWTService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception

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

        return AuthenticationResponse(code = HttpStatus.OK.value(), token = token, success = true)
    }

    fun authenticate(authenticateRequest: AuthenticationRequestDTO): AuthenticationResponse? {
        val token: String?

        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticateRequest.email, authenticateRequest.password))
            val user = userRepository.findByEmail(authenticateRequest.email).orElseThrow { UsernameNotFoundException("Usuário não encontrado") }
            token = jwtService.generateToken(user)
        } catch (e: BadCredentialsException) {
            return AuthenticationResponse(code = HttpStatus.BAD_REQUEST.value(), error = "As credenciais digitadas são inválidas, não foi possível realizar a autenticação.")
        } catch (e: AuthenticationException) {
            return AuthenticationResponse(code = HttpStatus.BAD_REQUEST.value(), error = "Ocorreu um erro na autenticação, verifique as credenciais digitadas e tente novamente. Se o erro persistir, contate o suporte.")
        } catch (e: Exception) {
            return AuthenticationResponse(code = HttpStatus.INTERNAL_SERVER_ERROR.value(), error = "Ocorreu um erro inesperado, porfavor, contate o suporte para obter maior apoio.")
        }

        return AuthenticationResponse(code = HttpStatus.OK.value(), token = token, success = true)
    }
}