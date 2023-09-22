package br.com.market.service.service

import br.com.market.service.dto.AuthenticationRequestDTO
import br.com.market.service.dto.UserDTO
import br.com.market.service.exeption.BusinessException
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
import java.util.regex.Pattern

@Service
class UserService(
    private val userRepository: IUserRepository,
    private val customUserRepository: ICustomUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(userDTO: UserDTO): AuthenticationResponse {
        validateUser(userDTO)

        val user = User(
            localId = userDTO.localId,
            name = userDTO.name,
            email = userDTO.email,
            password = passwordEncoder.encode(userDTO.password)
        )

        val token = jwtService.generateToken(user)
        user.token = token
        userRepository.save(user)

        return AuthenticationResponse(code = HttpStatus.OK.value(), token = token, success = true)
    }

    @Throws(BusinessException::class)
    private fun validateUser(dto: UserDTO) {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        when {
            dto.name.isEmpty() -> throw BusinessException("O nome é obrigatório.")
            dto.name.length > 255 -> throw BusinessException("O nome deve conter menos de 255 caractéres.")

            dto.email.isEmpty() -> throw BusinessException("O e-mail é obrigatório.")
            dto.email.length > 255 -> throw BusinessException("O e-mail deve conter menos de 255 caractéres.")
            !emailPattern.matcher(dto.email).matches() -> throw BusinessException("O e-mail é inválido.")
            !customUserRepository.isUniqueEmail(dto.email) -> throw BusinessException("E-mail já cadastrado.")

            dto.password.isEmpty() -> throw BusinessException("A senha é obrigatória.")
            dto.password.length > 255 -> throw BusinessException("A senha deve conter menos de 255 caractéres.")
        }
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

    fun findAllUserDTOs(marketId: Long): List<UserDTO> {
        return customUserRepository.findAll(marketId).map {
            UserDTO(
                id = it.id,
                localId = it.localId!!,
                name = it.name,
                email = it.email,
                password = passwordEncoder.encode(it.password),
                active = true,
                token = it.token,
                marketId = it.market!!.id
            )
        }
    }

    fun sync(userDTOs: List<UserDTO>) {
        userDTOs.forEach {
            val user = User(
                localId = it.localId,
                name = it.name,
                email = it.email,
                password = passwordEncoder.encode(it.password),
                id = it.id,
                token = it.token
            )

            userRepository.save(user)
        }
    }

}