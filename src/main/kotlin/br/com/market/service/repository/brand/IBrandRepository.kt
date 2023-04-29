package br.com.market.service.repository.brand

import br.com.market.service.models.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface IBrandRepository : JpaRepository<Brand, Long>, ICustomBrandRepository {

}