package br.com.market.service.dto

import br.com.market.service.dto.base.BaseDTO

data class MarketReadDTO(
    override var id: Long?,
    override var active: Boolean,
    var address: AddressDTO,
    var name: String,
    var companyId: Long,
    var company: CompanyDTO
): BaseDTO()
