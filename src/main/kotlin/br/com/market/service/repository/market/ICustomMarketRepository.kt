package br.com.market.service.repository.market

import br.com.market.service.dto.MarketReadDTO
import br.com.market.service.models.Market

interface ICustomMarketRepository {

    fun findByDeviceId(deviceId: String): Market?

    fun getListLovMarketReadDTO(simpleFilter: String?, marketId: Long, limit: Int, offset: Int): List<MarketReadDTO>
}
