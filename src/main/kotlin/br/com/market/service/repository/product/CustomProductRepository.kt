package br.com.market.service.repository.product

import br.com.market.service.models.Product2
import java.util.*

interface CustomProductRepository {

    fun findProductByLocalId(localId: UUID): Optional<Product2>

}