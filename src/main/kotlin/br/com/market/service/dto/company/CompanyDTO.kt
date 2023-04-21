package br.com.market.service.dto.company

import br.com.market.service.dto.base.BaseDTO
import br.com.market.service.dto.theme.ThemeDefinitionsDTO

data class CompanyDTO(
    var name: String,
    var themeDefinitionsDTO: ThemeDefinitionsDTO,
    override var id: Long? = null,
    override var active: Boolean = true
): BaseDTO()