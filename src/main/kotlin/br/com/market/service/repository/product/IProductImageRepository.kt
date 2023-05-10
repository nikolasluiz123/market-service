package br.com.market.service.repository.product

import br.com.market.service.models.ProductImage
import org.springframework.data.jpa.repository.JpaRepository

interface IProductImageRepository: JpaRepository<ProductImage, Long>, ICustomProductImageRepository {
}