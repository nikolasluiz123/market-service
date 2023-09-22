package br.com.market.service.dto

import br.com.market.service.dto.base.BaseDTO

data class CompanyDTO(
    var name: String,
    var themeDefinitionsDTO: ThemeDefinitionsDTO,
    override var id: Long? = null,
    override var active: Boolean = true
): BaseDTO()