package br.com.market.service.dto.auth

data class AuthenticationRequestDTO(
    var email: String = "",
    var password: String = ""
)
