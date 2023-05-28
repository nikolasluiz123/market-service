package br.com.market.service.repository.product

import br.com.market.service.models.ProductImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IProductImageRepository: JpaRepository<ProductImage, Long>