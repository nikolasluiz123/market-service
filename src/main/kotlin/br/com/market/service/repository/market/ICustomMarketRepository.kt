package br.com.market.service.repository.market

import br.com.market.service.models.Market

interface ICustomMarketRepository {

    fun findByDeviceId(deviceId: String): Market?
}
