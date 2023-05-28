package br.com.market.service.dto.brand

import br.com.market.service.dto.base.MobileDTO

data class CategoryBrandDTO(
    var localBrandId: String,
    var localCategoryId: String,
    var companyId: Long? = null,
    override var localId: String,
    override var active: Boolean = true,
    override var id: Long? = null
): MobileDTO()