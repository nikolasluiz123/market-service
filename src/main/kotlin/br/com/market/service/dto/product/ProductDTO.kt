package br.com.market.service.dto.product

import br.com.market.service.dto.base.MobileDTO
import br.com.market.service.models.enumeration.EnumUnit
import java.util.*

data class ProductDTO(
    override var active: Boolean = true,
    override var id: Long? = null,
    override var localId: UUID,
    var name: String? = null,
    var price: Double? = null,
    var quantity: Int? = null,
    var quantityUnit: EnumUnit? = null,
    var categoryBrandLocalId: UUID? = null,
    var companyId: Long? = null
): MobileDTO()