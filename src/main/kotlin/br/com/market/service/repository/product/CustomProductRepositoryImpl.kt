package br.com.market.service.repository.product

import br.com.market.service.controller.params.ProductServiceSearchParams
import br.com.market.service.dto.*
import br.com.market.service.extensions.getResultList
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Product
import br.com.market.service.models.enumeration.EnumUnit
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Tuple
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomProductRepositoryImpl : ICustomProductRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findProductByLocalId(localId: String): Product? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${Product::class.java.name} p ")
            add("WHERE p.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), Product::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findAll(marketId: Long, limit: Int?, offset: Int?): List<Product> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${Product::class.java.name} p ")
            add("WHERE p.market.id = :pMarketId")
        }

        params.add(Parameter(name = "pMarketId", value = marketId))

        val query = entityManager.createQuery(sql.toString(), Product::class.java)
        query.setParameters(params)
        query.maxResults = limit ?: Int.MAX_VALUE
        query.firstResult = offset ?: 0

        return query.resultList
    }

    override fun findProducts(simpleFilter: String?, limit: Int, offset: Int): List<ProductClientDTO> {
        val params = mutableListOf<Parameter>()
        val select = StringJoiner("\n\t")

        with(select) {
            add(" select p.id as productId, ")
            add("        p.local_id as productLocalId, ")
            add("        p.active as productActive, ")
            add("        p.name as productName, ")
            add("        p.price as productPrice, ")
            add("        p.quantity as productQuantity, ")
            add("        p.quantity_unit as productQuantityUnit, ")
            add("        cb.local_id as categoryBrandLocalId, ")
            add("        cb.id as categoryBrandId, ")
            add("        image.id as imageId, ")
            add("        image.local_id as imageLocalId, ")
            add("        image.bytes as imageBytes, ")
            add("        c.id as categoryId, ")
            add("        c.local_id as categoryLocalId, ")
            add("        c.name as categoryName, ")
            add("        b.id as brandId, ")
            add("        b.local_id as brandLocalId, ")
            add("        b.name as brandName, ")
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
            add(" from products p ")
            add(" inner join products_images image on image.product_id = p.id and image.principal ")
            add(" inner join categories_brands cb on cb.id = p.category_brand_id ")
            add(" inner join brands b on b.id = cb.brand_id ")
            add(" inner join categories c on c.id = cb.category_id ")
            add(" inner join markets m on m.id = p.market_id ")
            add(" inner join addresses ad on ad.id = m.address_id ")
            add(" inner join companies comp on comp.id = m.company_id ")
            add(" inner join theme_definitions theme on theme.id = comp.theme_definitions_id ")
        }

        val where = StringJoiner("\n\t")

        with(where) {
            add(" where p.active ")
            add(" and image.active ")

            if (!simpleFilter.isNullOrEmpty()) {
                add(" and p.name like :pSimpleFilter ")
                params.add(Parameter("pSimpleFilter", "%${simpleFilter}%"))
            }
        }

        val orderBy = StringJoiner("\n\t")

        with(orderBy) {
            add(" order by p.name ")
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

        val result = mutableListOf<ProductClientDTO>()
        query.getResultList(Tuple::class.java).forEach { tuple ->
            val position = tuple.get("productQuantityUnit", Short::class.javaObjectType)
            val enumUnit = EnumUnit.entries[position.toInt()]

            result.add(
                ProductClientDTO(
                    id = tuple.get("productId", Long::class.javaObjectType),
                    name = tuple.get("productName", String::class.javaObjectType),
                    price = tuple.get("productPrice", Double::class.javaObjectType),
                    quantity = tuple.get("productQuantity", Double::class.javaObjectType),
                    quantityUnit = enumUnit,
                    localId = tuple.get("productLocalId", String::class.javaObjectType),
                    marketId = tuple.get("marketId", Long::class.javaObjectType),
                    image = ProductImageDTO(
                        id = tuple.get("imageId", Long::class.javaObjectType),
                        localId = tuple.get("imageLocalId", String::class.javaObjectType),
                        bytes = tuple.get("imageBytes", ByteArray::class.javaObjectType),
                        productLocalId = tuple.get("productLocalId", String::class.javaObjectType),
                        principal = true,
                        marketId = tuple.get("marketId", Long::class.javaObjectType)
                    ),
                    category = CategoryDTO(
                        id = tuple.get("categoryId", Long::class.javaObjectType),
                        name = tuple.get("categoryName", String::class.javaObjectType),
                        localId = tuple.get("categoryLocalId", String::class.javaObjectType),
                        marketId = tuple.get("marketId", Long::class.javaObjectType)
                    ),
                    brand = BrandDTO(
                        id = tuple.get("brandId", Long::class.javaObjectType),
                        name = tuple.get("brandName", String::class.javaObjectType),
                        localId = tuple.get("brandLocalId", String::class.javaObjectType),
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
            )
        }

        return result
    }

    override fun getListProducts(params: ProductServiceSearchParams): List<ProductAndReferencesDTO> {
        val queryParams = mutableListOf<Parameter>()
        val select = StringJoiner("\n\t")

        with(select) {
            add(" select p.id as productId, ")
            add("        p.local_id as productLocalId, ")
            add("        p.active as productActive, ")
            add("        p.name as productName, ")
            add("        p.price as productPrice, ")
            add("        p.quantity as productQuantity, ")
            add("        p.quantity_unit as productQuantityUnit, ")
            add("        p.market_id as marketId, ")
            add("        cb.local_id as categoryBrandLocalId, ")
            add("        image.id as imageId, ")
            add("        image.local_id as imageLocalId, ")
            add("        image.bytes as imageBytes ")
        }

        val from = StringJoiner("\n\t")

        with(from) {
            add(" from products p ")
            add(" inner join products_images image on image.product_id = p.id and image.principal ")
            add(" inner join categories_brands cb on cb.id = p.category_brand_id ")
            add(" inner join categories c on c.id = cb.category_id ")
            add(" inner join brands b on b.id = cb.brand_id ")
        }

        val where = StringJoiner("\n\t")

        with(where) {
            add(" where p.active ")
            add(" and image.active ")

            if (!params.categoryId.isNullOrEmpty()) {
                add(" and c.local_id = :pCategoryId ")
                queryParams.add(Parameter("pCategoryId", params.categoryId))
            }

            if (!params.brandId.isNullOrEmpty()) {
                add(" and b.local_id = :pBrandId ")
                queryParams.add(Parameter("pBrandId", params.brandId))
            }

            if (!params.quickFilter.isNullOrEmpty()) {
                add(" and p.name like :pQuickFilter ")
                queryParams.add(Parameter("pQuickFilter", "%${params.quickFilter}%"))
            }
        }

        val orderBy = StringJoiner("\n\t")

        with(orderBy) {
            add(" order by p.name ")
        }

        val sql = StringJoiner("\r\n")
        with(sql) {
            add(select.toString())
            add(from.toString())
            add(where.toString())
            add(orderBy.toString())
            add("limit :pLimit offset :pOffset ")

            queryParams.add(Parameter("pLimit", params.limit!!))
            queryParams.add(Parameter("pOffset", params.offset!!))
        }

        val query = entityManager.createNativeQuery(sql.toString(), Tuple::class.java)
        query.setParameters(queryParams)

        val result = mutableListOf<ProductAndReferencesDTO>()
        query.getResultList(Tuple::class.java).forEach { tuple ->
            val position = tuple.get("productQuantityUnit", Short::class.javaObjectType)
            val enumUnit = EnumUnit.entries[position.toInt()]

            val dto = ProductAndReferencesDTO(
                product = ProductDTO(
                    id = tuple.get("productId", Long::class.javaObjectType),
                    name = tuple.get("productName", String::class.javaObjectType),
                    price = tuple.get("productPrice", Double::class.javaObjectType),
                    quantity = tuple.get("productQuantity", Double::class.javaObjectType),
                    quantityUnit = enumUnit,
                    localId = tuple.get("productLocalId", String::class.javaObjectType),
                    marketId = tuple.get("marketId", Long::class.javaObjectType),
                    categoryBrandLocalId = tuple.get("categoryBrandLocalId", String::class.javaObjectType)
                ),

                productImages = listOf(
                    ProductImageDTO(
                        id = tuple.get("imageId", Long::class.javaObjectType),
                        localId = tuple.get("imageLocalId", String::class.javaObjectType),
                        bytes = tuple.get("imageBytes", ByteArray::class.javaObjectType),
                        productLocalId = tuple.get("productLocalId", String::class.javaObjectType),
                        principal = true,
                        marketId = tuple.get("marketId", Long::class.javaObjectType)
                    )
                )
            )

            result.add(dto)
        }

        return result
    }

}