package br.com.market.service.service

import br.com.market.service.dto.*
import br.com.market.service.exeption.BusinessException
import br.com.market.service.models.User
import br.com.market.service.repository.device.IDeviceRepository
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
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(
    private val userRepository: IUserRepository,
    private val customUserRepository: ICustomUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val authenticationManager: AuthenticationManager,
    private val deviceRepository: IDeviceRepository
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

        return AuthenticationResponse(code = HttpStatus.OK.value(), success = true)
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
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticateRequest.email, authenticateRequest.password))
            val user = userRepository.findByEmail(authenticateRequest.email).orElseThrow { UsernameNotFoundException("Usuário não encontrado") }

            val device = deviceRepository.findById(authenticateRequest.tempDeviceId).getOrElse {
                throw BusinessException("O dispositivo com o identificador ${authenticateRequest.tempDeviceId} não foi cadastrado.")
            }

            val market = user.market!!
            val company = user.market?.company!!

            val result = AuthenticationResultDTO(
                company = CompanyDTO(
                    name = company.name,
                    themeDefinitions = ThemeDefinitionsDTO(
                        colorPrimary = company.themeDefinitions?.colorPrimary!!,
                        colorSecondary = company.themeDefinitions?.colorSecondary!!,
                        colorTertiary = company.themeDefinitions?.colorTertiary!!,
                        imageLogo = company.themeDefinitions?.imageLogo!!
                    ),
                    id = company.id!!
                ),
                market = MarketDTO(
                    id = market.id!!,
                    address = AddressDTO(
                        localId = market.address?.localId!!,
                        id = market.address?.id!!,
                        state = market.address?.state!!,
                        city = market.address?.city!!,
                        publicPlace = market.address?.publicPlace!!,
                        number = market.address?.number!!,
                        complement = market.address?.complement!!,
                        cep = market.address?.cep!!
                    ),
                    name = market.name!!,
                    companyId = company.id!!,
                ),
                device = DeviceDTO(
                    id = device.id!!,
                    name = device.name!!,
                    marketId = device.market?.id!!
                ),
                user = UserDTO(
                    localId = user.localId!!,
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    token = user.token,
                    marketId = user.market?.id!!
                ),
                token = jwtService.generateToken(user),
                userLocalId = user.localId!!
            )

            return AuthenticationResponse(result = result, code = HttpStatus.OK.value(), success = true)

        } catch (e: BadCredentialsException) {
            return AuthenticationResponse(code = HttpStatus.BAD_REQUEST.value(), error = "As credenciais digitadas são inválidas, não foi possível realizar a autenticação.")
        } catch (e: AuthenticationException) {
            return AuthenticationResponse(code = HttpStatus.BAD_REQUEST.value(), error = "Ocorreu um erro na autenticação, verifique as credenciais digitadas e tente novamente. Se o erro persistir, contate o suporte.")
        }
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

    fun registerAll(list: List<UserDTO>): AuthenticationResponse? {
        lateinit var response: AuthenticationResponse

        list.forEach {
            response = register(it)

            if (!response.success) {
                return response
            }
        }

        return response
    }

}