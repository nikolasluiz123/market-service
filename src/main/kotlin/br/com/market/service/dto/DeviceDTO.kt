package br.com.market.service.dto

data class DeviceDTO(
    var id: String? = null,
    var active: Boolean = true,
    var name: String? = null,
    var marketId: Long? = null
)