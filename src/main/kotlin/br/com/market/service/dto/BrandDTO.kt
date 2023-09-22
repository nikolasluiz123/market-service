package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO

data class BrandDTO(
    override var active: Boolean = true,
    override var id: Long? = null,
    override var localId: String,
    var name: String? = null,
    var marketId: Long? = null
): MobileDTO()