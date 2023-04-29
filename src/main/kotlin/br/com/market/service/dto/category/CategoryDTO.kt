package br.com.market.service.dto.category

import br.com.market.service.dto.base.MobileDTO
import java.util.*

data class CategoryDTO(
    var name: String? = null,
    var companyId: Long? = null,
    override var localId: UUID,
    override var id: Long? = null,
    override var active: Boolean = true
): MobileDTO()