package br.com.market.service.dto.product

import br.com.market.service.dto.base.MobileDTO
import br.com.market.service.models.enumeration.EnumUnit

data class ProductDTO(
    override var active: Boolean = true,
    override var id: Long? = null,
    override var localId: String,
    var name: String? = null,
    var price: Double? = null,
    var quantity: Double? = null,
    var quantityUnit: EnumUnit? = null,
    var categoryBrandLocalId: String? = null,
    var marketId: Long? = null
): MobileDTO()