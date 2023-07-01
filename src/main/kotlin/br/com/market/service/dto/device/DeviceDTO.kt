package br.com.market.service.dto.device

data class DeviceDTO(
    var id: String? = null,
    var active: Boolean = true,
    var name: String? = null,
    var companyId: Long? = null
)