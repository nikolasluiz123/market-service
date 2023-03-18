package br.com.market.service.response

class ReadResponse<DTO>(
    var values: List<DTO>,
    override var code: Int,
    override var success: Boolean,
    override var error: String? = null
) : IMarketServiceResponse