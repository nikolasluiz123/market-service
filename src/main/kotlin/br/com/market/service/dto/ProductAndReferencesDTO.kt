package br.com.market.service.dto

data class ProductAndReferencesDTO(
    val product: ProductDTO,
    val productImages: List<ProductImageDTO>
)