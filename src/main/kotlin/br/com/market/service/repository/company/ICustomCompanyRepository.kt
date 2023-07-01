package br.com.market.service.repository.company

import br.com.market.service.models.Company

interface ICustomCompanyRepository {

    fun findByDeviceId(deviceId: String): Company?
}
