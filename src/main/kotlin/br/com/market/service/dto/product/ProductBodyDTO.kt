package br.com.market.service.dto.product

data class ProductBodyDTO(
    val product: ProductDTO,
    val productImages: List<ProductImageDTO>
)