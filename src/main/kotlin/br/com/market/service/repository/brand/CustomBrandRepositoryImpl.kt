package br.com.market.service.repository.brand

import br.com.market.service.dto.BrandAndReferencesDTO
import br.com.market.service.dto.BrandDTO
import br.com.market.service.dto.CategoryBrandDTO
import br.com.market.service.extensions.getResultList
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Brand
import br.com.market.service.models.CategoryBrand
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

    override fun getListBrand(simpleFilter: String?, categoryLocalId: String?, marketId: Long, limit: Int, offset: Int): List<BrandAndReferencesDTO> {
        val params = mutableListOf<Parameter>()

        val select = StringJoiner("\n\t")
        with(select) {
            add(" select b.id as brandId, ")
            add("        b.local_id as brandLocalId, ")
            add("        b.name as brandName, ")
            add("        b.active as brandActive, ")
            add("        b.local_id as categoryLocalId, ")
            add("        b.market_id as brandMarketId, ")
            add("        cb.id as categoryBrandId, ")
            add("        cb.local_id as categoryBrandLocalId, ")
            add("        cb.market_id as categoryBrandMarketId, ")
            add("        c.local_id as categoryBrandCategoryLocalId ")
        }

        val from = StringJoiner("\n\t")
        with(from) {
            add(" from brands b ")
            add(" inner join categories_brands cb on cb.brand_id = b.id ")
            add(" inner join categories c on c.id = cb.category_id ")
        }

        val where = StringJoiner("\n\t")
        with(where) {
            add(" where b.active ")
            add(" and b.market_id = :pMarketId ")
            params.add(Parameter(name = "pMarketId", value = marketId))

            if (!categoryLocalId.isNullOrEmpty()) {
                add(" and c.local_id = :pCategoryId ")
                params.add(Parameter(name = "pCategoryId", value = categoryLocalId))
            }

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

        val tuples = query.getResultList(Tuple::class.java)

        return tuples.map { tuple ->
            BrandAndReferencesDTO(
                brand = BrandDTO(
                    active = true,
                    id = tuple.get("brandId", Long::class.javaObjectType),
                    localId = tuple.get("brandLocalId", String::class.javaObjectType),
                    name = tuple.get("brandName", String::class.javaObjectType),
                    marketId = tuple.get("brandMarketId", Long::class.javaObjectType)
                ),
                categoryBrand = CategoryBrandDTO(
                    active = true,
                    localId = tuple.get("categoryBrandLocalId", String::class.javaObjectType),
                    localCategoryId = tuple.get("categoryBrandCategoryLocalId", String::class.javaObjectType),
                    localBrandId = tuple.get("brandLocalId", String::class.javaObjectType)
                )
            )
        }
    }

    override fun findBrandAndReferenceBy(categoryLocalId: String, brandLocalId: String): BrandAndReferencesDTO {
        val params = mutableListOf<Parameter>()

        val select = StringJoiner("\n\t")

        with(select) {
            add(" select b.id as brandId, ")
            add("        b.name as brandName, ")
            add("        b.market.id as marketId, ")
            add("        b.active as brandActive, ")
            add("        b.localId as brandLocalId, ")
            add("        cb.id as categoryBrandId, ")
            add("        cb.localId as categoryBrandLocalId, ")
            add("        c.localId as categoryLocalId ")
        }

        val from = StringJoiner("\n\t")

        with(from) {
            add(" from ${CategoryBrand::class.java.name} cb ")
            add(" inner join cb.brand b ")
            add(" inner join cb.category c ")
        }

        val where = StringJoiner("\n\t")

        with(where) {
            add(" where b.localId = :pBrandId ")
            add(" and c.localId = :pCategoryId ")
        }

        params.add(Parameter(name = "pCategoryId", value = categoryLocalId))
        params.add(Parameter(name = "pBrandId", value = brandLocalId))

        val query = entityManager.createQuery(select.toString(), Tuple::class.java)
        query.setParameters(params)

        val tuple = query.singleResult

        return BrandAndReferencesDTO(
            brand = BrandDTO(
                id = tuple.get("brandId", Long::class.javaObjectType),
                active = tuple.get("brandActive", Boolean::class.javaObjectType),
                localId = tuple.get("brandLocalId", String::class.javaObjectType),
                name = tuple.get("brandName", String::class.javaObjectType),
                marketId = tuple.get("marketId", Long::class.javaObjectType)
            ),
            categoryBrand = CategoryBrandDTO(
                id = tuple.get("categoryBrandId", Long::class.javaObjectType),
                localBrandId = tuple.get("brandLocalId", String::class.javaObjectType),
                localCategoryId = tuple.get("categoryLocalId", String::class.javaObjectType),
                marketId = tuple.get("marketId", Long::class.javaObjectType),
                localId = tuple.get("categoryBrandLocalId", String::class.javaObjectType)
            )
        )
    }
}