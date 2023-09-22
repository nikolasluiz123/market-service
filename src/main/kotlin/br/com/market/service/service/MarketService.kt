package br.com.market.service.service

import br.com.market.service.dto.AddressDTO
import br.com.market.service.dto.MarketDTO
import br.com.market.service.models.Address
import br.com.market.service.models.Market
import br.com.market.service.repository.address.IAddressRepository
import br.com.market.service.repository.company.ICompanyRepository
import br.com.market.service.repository.market.ICustomMarketRepository
import br.com.market.service.repository.market.IMarketRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class MarketService(
    private val marketRepository: IMarketRepository,
    private val customMarketRepository: ICustomMarketRepository,
    private val addressRepository: IAddressRepository,
    private val companyRepository: ICompanyRepository
) {

    fun save(marketDTO: MarketDTO) {
        lateinit var address: Address

        with(marketDTO.address!!) {
            address = if (id != null) {
                addressRepository.findById(id!!).get()
            } else {
                Address(
                    active = active,
                    localId = localId,
                    state = state,
                    city = city,
                    publicPlace = publicPlace,
                    number = number,
                    complement = complement,
                    cep = cep
                )
            }

            address = addressRepository.save(address)
        }

        with(marketDTO) {
            val market = if (marketDTO.id != null) {
                marketRepository.findById(id!!).get()
            } else {
                Market(
                    name = name,
                    company = companyRepository.findById(companyId!!).get(),
                    address = address,
                    active = active
                )
            }

            marketRepository.save(market)
        }
    }

    fun findAll(): List<MarketDTO> {
        return marketRepository.findAll().map {
            MarketDTO(
                id = it.id,
                name = it.name,
                companyId = it.company?.id,
                active = it.active,
                address = AddressDTO(
                    localId = it.address?.localId!!,
                    id = it.address?.id!!,
                    active = it.address?.active!!,
                    state = it.address?.state!!,
                    city = it.address?.city!!,
                    publicPlace = it.address?.publicPlace!!,
                    number = it.address?.number!!,
                    complement = it.address?.complement!!,
                    cep = it.address?.cep!!
                )
            )
        }
    }

    fun toggleActive(companyId: Long) {
        marketRepository.findById(companyId).getOrNull()?.let {
            marketRepository.save(it.copy(active = !it.active))

            val address = addressRepository.findById(it.address?.id!!).get().copy(active = !it.address!!.active)
            addressRepository.save(address)
        }
    }

    fun findByDeviceId(deviceId: String): MarketDTO {
        return customMarketRepository.findByDeviceId(deviceId)!!.run {
            MarketDTO(
                id = id,
                active = active,
                name = name,
                companyId = company?.id,
                address = AddressDTO(
                    localId = address?.localId!!,
                    id = address?.id!!,
                    active = address?.active!!,
                    state = address?.state!!,
                    city = address?.city!!,
                    publicPlace = address?.publicPlace!!,
                    number = address?.number!!,
                    complement = address?.complement!!,
                    cep = address?.cep!!
                )
            )
        }
    }
}