package br.com.market.service.repository.product

import br.com.market.service.models.Product2
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product2, Long>, CustomProductRepository {
}