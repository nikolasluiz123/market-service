package br.com.market.service.dto.market

import br.com.market.service.dto.address.AddressDTO
import br.com.market.service.dto.base.BaseDTO

data class MarketDTO(
    override var id: Long? = null,
    override var active: Boolean = true,
    var address: AddressDTO? = null,
    var name: String? = null,
    var companyId: Long? = null
): BaseDTO()