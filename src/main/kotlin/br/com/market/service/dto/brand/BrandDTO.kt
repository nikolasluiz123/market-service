package br.com.market.service.dto.brand

import br.com.market.service.dto.base.BaseDTO
import java.util.*

data class BrandDTO(
    override var active: Boolean = true,
    override var id: Long? = null,
    var localBrandId: UUID,
    var name: String? = null,
    var companyId: Long? = null
): BaseDTO()