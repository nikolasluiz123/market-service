package br.com.market.service.repository.category

import br.com.market.service.models.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
}