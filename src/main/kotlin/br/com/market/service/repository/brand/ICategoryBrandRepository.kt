package br.com.market.service.repository.brand

import br.com.market.service.models.CategoryBrand
import org.springframework.data.jpa.repository.JpaRepository

interface ICategoryBrandRepository: JpaRepository<CategoryBrand, Long> {
}