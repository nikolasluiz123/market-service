package br.com.market.service.repository

import br.com.market.service.models.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long> {

}