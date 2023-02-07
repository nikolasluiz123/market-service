package br.com.market.service.repository

import br.com.market.service.models.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
}