package br.com.market.service.repository.brand

import br.com.market.service.dto.brand.BrandView

interface CustomBrandRepository {

    fun findProductBrands(productId: Long): List<BrandView>
}