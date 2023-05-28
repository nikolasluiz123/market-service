package br.com.market.service.repository.product

import br.com.market.service.models.Product
import org.springframework.data.jpa.repository.JpaRepository

interface IProductRepository : JpaRepository<Product, Long>