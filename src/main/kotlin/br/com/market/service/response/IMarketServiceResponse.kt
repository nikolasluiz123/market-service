package br.com.market.service.response

import java.io.Serializable

interface IMarketServiceResponse : Serializable {
    var code: Int
    var success: Boolean
    var error: String?
}