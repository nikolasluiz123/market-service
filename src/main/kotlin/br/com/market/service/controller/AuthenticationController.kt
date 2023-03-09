package br.com.market.service.controller

import br.com.market.service.dto.auth.AuthenticationRequestDTO
import br.com.market.service.dto.auth.RegisterRequestDTO
import br.com.market.service.response.AuthenticationResponse
import br.com.market.service.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val service: AuthenticationService) {

    @PostMapping("/register")
    @Transactional(timeout = 600)
    fun register(@RequestBody registerRequest: RegisterRequestDTO): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.register(registerRequest))
    }

    @PostMapping("/authenticate")
    @Transactional(timeout = 600)
    fun authenticate(@RequestBody authenticateRequest: AuthenticationRequestDTO): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.authenticate(authenticateRequest))
    }
}