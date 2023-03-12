package br.com.market.service.repository.brand

import br.com.market.service.dto.brand.BrandView
import br.com.market.service.models.Brand
import java.util.*

interface CustomBrandRepository {

    fun findProductBrands(productId: Long): List<BrandView>

    fun findBrandByLocalId(localBrandId: Long): Optional<Brand>
}