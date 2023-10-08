package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO
import br.com.market.service.models.enumeration.EnumUnit

data class ProductClientDTO(
    override var localId: String,
    var name: String,
    var price: Double,
    var quantity: Double,
    var quantityUnit: EnumUnit,
    var image: ProductImageDTO,
    var brand: BrandDTO,
    var category: CategoryDTO,
    var categoryBrand: CategoryBrandDTO,
    var market: MarketDTO,
    var company: CompanyDTO,
    override var id: Long? = null,
    override var active: Boolean = true,
    var marketId: Long? = null
): MobileDTO() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductClientDTO

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
