package br.com.market.service.repository.brand

import br.com.market.service.models.Brand
import java.util.*

interface ICustomBrandRepository {

    fun findBrandByLocalId(localId: UUID): Brand?
}