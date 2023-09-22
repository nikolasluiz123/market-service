package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO

data class CategoryDTO(
    var name: String? = null,
    var marketId: Long? = null,
    override var localId: String,
    override var id: Long? = null,
    override var active: Boolean = true
): MobileDTO()