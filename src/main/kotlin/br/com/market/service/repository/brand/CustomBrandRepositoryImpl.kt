package br.com.market.service.repository.brand

import br.com.market.service.dto.*
import br.com.market.service.extensions.getResultList
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Brand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Tuple
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomBrandRepositoryImpl : ICustomBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findBrandByLocalId(localId: String): Brand? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${Brand::class.java.name} c ")
            add("WHERE c.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), Brand::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findBrands(marketId: Long, limit: Int?, offset: Int?): List<Brand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${Brand::class.java.name} c ")
            add("WHERE c.market.id = :pMarketId")
            add("order by c.name")
        }

        params.add(Parameter(name = "pMarketId", value = marketId))

        val query = entityManager.createQuery(sql.toString(), Brand::class.java)
        query.setParameters(params)
        query.maxResults = limit ?: Int.MAX_VALUE
        query.firstResult = offset ?: 0

        return query.resultList
    }

    override fun getListLovBrandReadDTO(simpleFilter: String?, marketId: Long, limit: Int, offset: Int): List<BrandReadDTO> {
        val params = mutableListOf<Parameter>()

        val select = StringJoiner("\n\t")
        with(select) {
            add(" select b.id as brandId, ")
            add("        b.local_id as brandLocalId, ")
            add("        b.name as brandName, ")
            add("        b.active as brandActive, ")
            add("        c.id as categoryId, ")
            add("        b.local_id as categoryLocalId, ")
            add("        c.name as categoryName, ")
            add("        c.active as categoryActive, ")
            add("        cb.id as categoryBrandId, ")
            add("        cb.local_id as categoryBrandLocalId, ")
            add("        m.id as marketId, ")
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
            add(" from brands b ")
            add(" inner join categories_brands cb on cb.brand_id = b.id ")
            add(" inner join categories c on c.id = cb.category_id ")
            add(" inner join markets m on m.id = b.market_id ")
            add(" inner join addresses ad on ad.id = m.address_id ")
            add(" inner join companies comp on comp.id = m.company_id ")
            add(" inner join theme_definitions theme on theme.id = comp.theme_definitions_id ")
        }

        val where = StringJoiner("\n\t")
        with(where) {
            add(" where b.active ")
            add(" and m.id = :pMarketId ")

            params.add(Parameter(name = "pMarketId", value = marketId))

            if (!simpleFilter.isNullOrEmpty()) {
                add(" and b.name like :pSimpleFilter ")
                params.add(Parameter("pSimpleFilter", "%${simpleFilter}%"))
            }
        }

        val orderBy = StringJoiner("\n\t")
        with(orderBy) {
            add(" order by b.name ")
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
            BrandReadDTO(
                id = tuple.get("brandId", Long::class.javaObjectType),
                name = tuple.get("brandName", String::class.javaObjectType),
                localId = tuple.get("brandLocalId", String::class.javaObjectType),
                marketId = tuple.get("marketId", Long::class.javaObjectType),
                active = tuple.get("brandActive", Boolean::class.javaObjectType),
                category = CategoryDTO(
                    id = tuple.get("categoryId", Long::class.javaObjectType),
                    name = tuple.get("categoryName", String::class.javaObjectType),
                    localId = tuple.get("categoryLocalId", String::class.javaObjectType),
                    marketId = tuple.get("marketId", Long::class.javaObjectType)
                ),
                categoryBrand = CategoryBrandDTO(
                    id = tuple.get("categoryBrandId", Long::class.javaObjectType),
                    localId = tuple.get("categoryBrandLocalId", String::class.javaObjectType),
                    localBrandId = tuple.get("brandLocalId", String::class.javaObjectType),
                    localCategoryId = tuple.get("categoryLocalId", String::class.javaObjectType),
                    marketId = tuple.get("marketId", Long::class.javaObjectType)
                ),
                market = MarketDTO(
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
                    companyId = tuple.get("companyId", Long::class.javaObjectType)
                ),
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
                )
            )
        }
    }
}