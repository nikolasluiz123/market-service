package br.com.market.service.repository.product

import br.com.market.service.models.ProductBrand2
import org.springframework.data.jpa.repository.JpaRepository

interface ProductBrandRepository : JpaRepository<ProductBrand2, Long> , CustomProductBrandRepository {

}