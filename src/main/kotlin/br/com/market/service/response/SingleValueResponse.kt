package br.com.market.service.response

data class SingleValueResponse<T>(
    var value: T?,
    override var code: Int,
    override var success: Boolean = false,
    override var error: String? = null
): IMarketServiceResponse