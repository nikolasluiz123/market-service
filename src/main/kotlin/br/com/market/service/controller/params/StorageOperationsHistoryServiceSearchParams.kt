package br.com.market.service.controller.params

import br.com.market.service.controller.filters.MovementFilters
import br.com.market.service.controller.params.interfaces.IServicePaginatedSearchParams


data class StorageOperationsHistoryServiceSearchParams(
    var filters: MovementFilters,
    override var limit: Int? = null,
    override var offset: Int? = null,
): IServicePaginatedSearchParams