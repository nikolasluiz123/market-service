package br.com.market.service.dto.brand

import br.com.market.service.dto.base.MobileDTO
import java.util.*

data class CategoryBrandDTO(
    var localBrandId: UUID,
    var localCategoryId: UUID,
    var companyId: Long? = null,
    override var localId: UUID,
    override var active: Boolean = true,
    override var id: Long? = null
): MobileDTO()