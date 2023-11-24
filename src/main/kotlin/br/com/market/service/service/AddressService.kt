package br.com.market.service.service

import br.com.market.service.dto.AddressDTO
import br.com.market.service.models.Address
import br.com.market.service.repository.address.IAddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(private val addressRepository: IAddressRepository) {
    
    fun saveAll(list: List<AddressDTO>) {
        val addresses = list.map {
            Address(
                localId = it.localId,
                state = it.state,
                city = it.city,
                publicPlace = it.publicPlace,
                number = it.number,
                complement = it.complement,
                cep = it.cep
            )
        }

        addressRepository.saveAll(addresses)
    }
}