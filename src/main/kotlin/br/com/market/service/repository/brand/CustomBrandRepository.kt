package br.com.market.service.repository.brand

import br.com.market.service.dto.brand.BrandView
import br.com.market.service.models.Brand
import java.util.*

interface CustomBrandRepository {

    fun findProductBrands(productId: UUID): List<BrandView>

    fun findBrandByLocalId(localBrandId: UUID): Optional<Brand>
}