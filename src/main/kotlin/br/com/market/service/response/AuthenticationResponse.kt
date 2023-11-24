package br.com.market.service.response

import br.com.market.service.dto.AuthenticationResultDTO

data class AuthenticationResponse(
    var result: AuthenticationResultDTO? = null,
    override var code: Int = 0,
    override var success: Boolean = false,
    override var error: String? = null
): IMarketServiceResponse
