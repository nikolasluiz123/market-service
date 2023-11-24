package br.com.market.service.dto

data class AuthenticationRequestDTO(
    var email: String = "",
    var password: String = "",
    var tempDeviceId: String = ""
)
