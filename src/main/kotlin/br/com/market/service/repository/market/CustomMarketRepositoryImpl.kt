package br.com.market.service.repository.market

import br.com.market.service.dto.AddressDTO
import br.com.market.service.dto.CompanyDTO
import br.com.market.service.dto.MarketReadDTO
import br.com.market.service.dto.ThemeDefinitionsDTO
import br.com.market.service.extensions.getResultList
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Device
import br.com.market.service.models.Market
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Tuple
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomMarketRepositoryImpl : ICustomMarketRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findByDeviceId(deviceId: String): Market? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT m ")
            add("FROM ${Device::class.java.name} d ")
            add("INNER JOIN d.market m ")
            add("WHERE d.id = :pDeviceId")
        }

        params.add(Parameter(name = "pDeviceId", value = deviceId))

        val query = entityManager.createQuery(sql.toString(), Market::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun getListLovMarketReadDTO(simpleFilter: String?, marketId: Long, limit: Int, offset: Int): List<MarketReadDTO> {
        val params = mutableListOf<Parameter>()

        val select = StringJoiner("\n\t")
        with(select) {
            add(" select m.id as marketId, ")
            add("        m.name as marketName, ")
            add("        m.company_id as marketCompanyId, ")
            add("        m.address_id as marketAddressId, ")
            add("        ad.id as addressId, ")
            add("        ad.local_id as addressLocalId, ")
            add("        ad.state as addressState, ")
            add("        ad.city as addressCity, ")
            add("        ad.public_place as addressPublicPlace, ")
            add("        ad.number as addressNumber, ")
            add("        ad.complement as addressComplement, ")
            add("        ad.cep as addressCep, ")
            add("        comp.id as companyId, ")
            add("        comp.name as companyName, ")
            add("        theme.id as themeId, ")
            add("        theme.color_primary as themeColorPrimary, ")
            add("        theme.color_secondary as themeColorSecondary, ")
            add("        theme.color_tertiary as themeColorTertiary, ")
            add("        theme.image_logo as themeLogo ")
        }

        val from = StringJoiner("\n\t")
        with(from) {
            add(" from markets m on m.id = b.market_id ")
            add(" inner join addresses ad on ad.id = m.address_id ")
            add(" inner join companies comp on comp.id = m.company_id ")
            add(" inner join theme_definitions theme on theme.id = comp.theme_definitions_id ")
        }

        val where = StringJoiner("\n\t")
        with(where) {
            add(" where m.active ")

            if (!simpleFilter.isNullOrEmpty()) {
                add(" and m.name like :pSimpleFilter ")
                params.add(Parameter("pSimpleFilter", "%${simpleFilter}%"))
            }
        }

        val orderBy = StringJoiner("\n\t")
        with(orderBy) {
            add(" order by m.name ")
        }

        val sql = StringJoiner("\r\n")
        with(sql) {
            add(select.toString())
            add(from.toString())
            add(where.toString())
            add(orderBy.toString())
            add("limit :pLimit offset :pOffset ")

            params.add(Parameter("pLimit", limit))
            params.add(Parameter("pOffset", offset))
        }

        val query = entityManager.createNativeQuery(sql.toString(), Tuple::class.java)
        query.setParameters(params)
        query.maxResults = limit
        query.firstResult = offset

        val tuples = query.getResultList(Tuple::class.java)

        return tuples.map { tuple ->
            MarketReadDTO(
                id = tuple.get("marketId", Long::class.javaObjectType),
                address = AddressDTO(
                    id = tuple.get("addressId", Long::class.javaObjectType),
                    localId = tuple.get("addressLocalId", String::class.javaObjectType),
                    state = tuple.get("addressState", String::class.javaObjectType),
                    city = tuple.get("addressCity", String::class.javaObjectType),
                    publicPlace = tuple.get("addressPublicPlace", String::class.javaObjectType),
                    number = tuple.get("addressNumber", String::class.javaObjectType),
                    complement = tuple.get("addressComplement", String::class.javaObjectType),
                    cep = tuple.get("addressCep", String::class.javaObjectType)
                ),
                name = tuple.get("marketName", String::class.javaObjectType),
                companyId = tuple.get("companyId", Long::class.javaObjectType),
                company = CompanyDTO(
                    id = tuple.get("companyId", Long::class.javaObjectType),
                    name = tuple.get("companyName", String::class.javaObjectType),
                    themeDefinitions = ThemeDefinitionsDTO(
                        id = tuple.get("themeId", Long::class.javaObjectType),
                        colorPrimary = tuple.get("themeColorPrimary", String::class.javaObjectType),
                        colorSecondary = tuple.get("themeColorSecondary", String::class.javaObjectType),
                        colorTertiary = tuple.get("themeColorTertiary", String::class.javaObjectType),
                        imageLogo = tuple.get("themeLogo", ByteArray::class.javaObjectType)
                    )
                ),
                active = true
            )
        }
    }
}