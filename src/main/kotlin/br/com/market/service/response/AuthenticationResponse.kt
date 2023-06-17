package br.com.market.service.response

data class AuthenticationResponse(
    var token: String? = null,
    var userLocalId: String? = null,
    override var code: Int = 0,
    override var success: Boolean = false,
    override var error: String? = null
): IMarketServiceResponse
