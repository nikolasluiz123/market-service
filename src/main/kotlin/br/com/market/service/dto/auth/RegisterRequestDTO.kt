package br.com.market.service.dto.auth

data class RegisterRequestDTO(
    var name: String = "",
    var email: String = "",
    var password: String = ""
)
