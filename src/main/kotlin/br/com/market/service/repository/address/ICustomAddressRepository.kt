package br.com.market.service.repository.address

import br.com.market.service.models.Address

interface ICustomAddressRepository {

    fun findAddressByLocalId(localId: String): Address?
}