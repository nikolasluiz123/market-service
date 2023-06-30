package br.com.market.service.dto.device

import br.com.market.service.dto.base.BaseDTO

data class DeviceDTO(
    override var id: Long? = null,
    override var active: Boolean = true,
    var name: String? = null,
    var imei: String? = null,
    var companyId: Long? = null
) : BaseDTO()