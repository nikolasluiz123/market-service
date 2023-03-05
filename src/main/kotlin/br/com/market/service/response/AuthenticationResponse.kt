package br.com.market.service.response

data class AuthenticationResponse(
    var code: Int = 0,
    var token: String? = null,
    var success: Boolean = false,
    var error: String? = null
)
