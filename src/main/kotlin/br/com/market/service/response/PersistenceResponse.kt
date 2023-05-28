package br.com.market.service.response

import java.util.*

data class PersistenceResponse(
    var idLocal: String? = null,
    var idRemote: Long? = null,
    override var code: Int = 0,
    override var success: Boolean = false,
    override var error: String? = null
): IMarketServiceResponse
