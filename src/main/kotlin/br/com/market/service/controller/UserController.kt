package br.com.market.service.controller

import br.com.market.service.dto.AuthenticationRequestDTO
import br.com.market.service.dto.UserDTO
import br.com.market.service.response.AuthenticationResponse
import br.com.market.service.response.MarketServiceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val service: UserService) {

    @PostMapping("/register")
    @Transactional(timeout = 600)
    fun register(@RequestBody registerRequest: UserDTO): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.register(registerRequest))
    }

    @PostMapping("/authenticate")
    @Transactional(timeout = 600)
    fun authenticate(@RequestBody authenticateRequest: AuthenticationRequestDTO): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.authenticate(authenticateRequest))
    }

    @GetMapping
    @Transactional(timeout = 600)
    fun findAll(@RequestParam marketId: Long): ResponseEntity<ReadResponse<UserDTO>> {
        val values = service.findAllUserDTOs(marketId)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/sync")
    @Transactional(timeout = 600)
    fun sync(@RequestBody @Valid userDTOs: List<UserDTO>): ResponseEntity<MarketServiceResponse> {
        service.sync(userDTOs)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

}