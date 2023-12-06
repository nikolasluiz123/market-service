package br.com.market.service.controller.params

import br.com.market.service.controller.params.interfaces.IServicePaginatedSearchParams
import br.com.market.service.controller.params.interfaces.IServiceQuickFilterParams

data class ProductServiceSearchParams(
    val categoryId: String? = null,
    val brandId: String? = null,
    override val quickFilter: String?,
    override var limit: Int? = null,
    override var offset: Int? = null,
): IServicePaginatedSearchParams, IServiceQuickFilterParams