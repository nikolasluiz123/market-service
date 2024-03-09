package br.com.market.service.dto

data class MarketImportConfigDTO(
    val importMarketId: Long? = null,
    val importCategory: Boolean = false,
    val importBrand: Boolean = false,
    val importProduct: Boolean = false
)