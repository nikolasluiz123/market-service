package br.com.market.service.repository.brand

import br.com.market.service.models.Brand2
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand2, Long>, CustomBrandRepository {

}