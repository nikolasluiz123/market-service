package br.com.market.service.service

import br.com.market.service.dto.auth.AuthenticationRequestDTO
import br.com.market.service.dto.auth.UserDTO
import br.com.market.service.models.User
import br.com.market.service.repository.user.ICustomUserRepository
import br.com.market.service.repository.user.IUserRepository
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

@Service
class UserService(
    private val userRepository: IUserRepository,
    private val customUserRepository: ICustomUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(registerRequest: UserDTO): AuthenticationResponse? {
        val user = User(
            localId = registerRequest.localId,
            name = registerRequest.name,
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password)
        )

        val token = jwtService.generateToken(user)
        user.token = token
        userRepository.save(user)

        return AuthenticationResponse(code = HttpStatus.OK.value(), token = token, success = true)
    }

    fun authenticate(authenticateRequest: AuthenticationRequestDTO): AuthenticationResponse? {
        val token: String?
        val userLocalId: String?

        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticateRequest.email, authenticateRequest.password))
            val user = userRepository.findByEmail(authenticateRequest.email).orElseThrow { UsernameNotFoundException("Usuário não encontrado") }

            token = jwtService.generateToken(user)
            userLocalId = user.localId

        } catch (e: BadCredentialsException) {
            return AuthenticationResponse(code = HttpStatus.BAD_REQUEST.value(), error = "As credenciais digitadas são inválidas, não foi possível realizar a autenticação.")
        } catch (e: AuthenticationException) {
            return AuthenticationResponse(code = HttpStatus.BAD_REQUEST.value(), error = "Ocorreu um erro na autenticação, verifique as credenciais digitadas e tente novamente. Se o erro persistir, contate o suporte.")
        } catch (e: Exception) {
            return AuthenticationResponse(code = HttpStatus.INTERNAL_SERVER_ERROR.value(), error = "Ocorreu um erro inesperado, porfavor, contate o suporte para obter maior apoio.")
        }

        return AuthenticationResponse(code = HttpStatus.OK.value(), token = token, userLocalId = userLocalId, success = true)
    }

    fun findAllUserDTOs(): List<UserDTO> {
        return userRepository.findAll().map {
            UserDTO(
                id = it.id,
                localId = it.localId!!,
                name = it.name,
                email = it.email,
                password = passwordEncoder.encode(it.password),
                active = true
            )
        }
    }

    fun sync(userDTOs: List<UserDTO>) {
        userDTOs.forEach {
            val user = User(
                localId = it.localId,
                name = it.name,
                email = it.email,
                password = passwordEncoder.encode(it.password)
            )

            userRepository.save(user)
        }
    }

}