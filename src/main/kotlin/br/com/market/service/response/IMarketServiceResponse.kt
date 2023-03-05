package br.com.market.service.response

interface IMarketServiceResponse {
    var code: Int
    var success: Boolean
    var error: String?
}