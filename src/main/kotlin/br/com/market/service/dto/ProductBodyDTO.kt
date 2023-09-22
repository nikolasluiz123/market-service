package br.com.market.service.dto

data class ProductBodyDTO(
    val product: ProductDTO,
    val productImages: List<ProductImageDTO>
)