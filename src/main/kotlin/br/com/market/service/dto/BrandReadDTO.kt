package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO

data class BrandReadDTO(
    override var id: Long?,
    override var active: Boolean,
    override var localId: String,
    var name: String,
    var marketId: Long,
    var category: CategoryDTO,
    var categoryBrand: CategoryBrandDTO,
    var market: MarketDTO,
    var company: CompanyDTO
): MobileDTO()