package br.com.market.service.repository

import br.com.market.service.models.ProductBrand
import org.springframework.data.jpa.repository.JpaRepository

interface ProductBrandRepository : JpaRepository<ProductBrand, Long> , CustomProductBrandRepository {

}