package br.com.market.service.response

data class MarketServiceResponse(
    override var code: Int = 0,
    override var success: Boolean = false,
    override var error: String? = null
) : IMarketServiceResponse