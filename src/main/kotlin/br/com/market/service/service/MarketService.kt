package br.com.market.service.service

import br.com.market.service.dto.AddressDTO
import br.com.market.service.dto.MarketDTO
import br.com.market.service.dto.MarketReadDTO
import br.com.market.service.models.Address
import br.com.market.service.models.Market
import br.com.market.service.repository.address.IAddressRepository
import br.com.market.service.repository.brand.ICustomBrandRepository
import br.com.market.service.repository.category.ICustomCategoryRepository
import br.com.market.service.repository.company.ICompanyRepository
import br.com.market.service.repository.market.ICustomMarketRepository
import br.com.market.service.repository.market.IMarketRepository
import br.com.market.service.repository.product.ICustomProductRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class MarketService(
    private val marketRepository: IMarketRepository,
    private val customMarketRepository: ICustomMarketRepository,
    private val addressRepository: IAddressRepository,
    private val companyRepository: ICompanyRepository,
    private val customCategoryRepository: ICustomCategoryRepository,
    private val customBrandRepository: ICustomBrandRepository,
    private val customProductRepository: ICustomProductRepository
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

    fun getListLovMarketReadSDO(simpleFilter: String?, marketId: Long, limit: Int, offset: Int): List<MarketReadDTO> {
        return customMarketRepository.getListLovMarketReadDTO(simpleFilter, marketId, limit, offset)
    }

    /**
     * Método para importar as informações de um mercado para o outro
     */
    fun import(marketDTO: MarketDTO) {
        val config = marketDTO.config
        val marketId = marketDTO.id!!

        if (config != null && config.importCategory) {
            importCategory(config.importMarketId!!, marketId)

            if (config.importBrand) {
                importBrand(config.importMarketId, marketId)
            }

            if (config.importProduct) {
                importProduct(config.importMarketId, marketId)
            }
        }
    }

    private fun importCategory(importMarketId: Long, marketId: Long) {
        // val listCategory = customCategoryRepository.getListCategory(simpleFilter = null, marketId = importMarketId, limit = null, offset = null)
        TODO("Not yet implemented")
    }

    private fun importBrand(importMarketId: Long, marketId: Long) {
        TODO("Not yet implemented")
    }

    private fun importProduct(importMarketId: Long, marketId: Long) {
        TODO("Not yet implemented")
    }
}