package br.com.market.service.repository.product

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Product
import br.com.market.service.models.ProductBrand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.util.*

class ProductBrandRepositoryImpl : CustomProductBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findByLocalProductId(localProductId: Long): List<ProductBrand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${ProductBrand::class.java.name} p ")
            add("WHERE p.product.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localProductId))

        val query = entityManager.createQuery(sql.toString(), ProductBrand::class.java)
        query.setParameters(params)

        return query.resultList
    }

    override fun findByLocalBrandId(localBrandId: Long): Optional<ProductBrand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT pb")
            add("FROM ${ProductBrand::class.java.name} pb ")
            add("WHERE pb.brand.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localBrandId))

        val query = entityManager.createQuery(sql.toString(), ProductBrand::class.java)
        query.setParameters(params)

        return try {
            Optional.of(query.singleResult)
        } catch (e: Exception) {
            Optional.empty<ProductBrand>()
        }
    }

//    override fun sumStorageCount(storageDTO: UpdateStorageDTO) {
//        if (notHaveDataForUpdate(storageDTO)){
//            throw NoResultException("Não há registro de estoque para o Produto e/ou Marca especificado(s)")
//        }
//
//        val params = mutableListOf<Parameter>()
//        val sql = StringJoiner("\n\t")
//
//        with(sql) {
//            add("UPDATE ${ProductBrand::class.java.name}")
//            add("SET count = count + :pCount")
//            add("WHERE product.id = :pProductId AND brand.id = :pBrandId")
//        }
//
//        params.add(Parameter("pCount", storageDTO.count))
//        params.add(Parameter("pProductId", storageDTO.productId))
//        params.add(Parameter("pBrandId", storageDTO.brandId))
//
//        val query = entityManager.createQuery(sql.toString())
//        query.setParameters(params)
//
//        query.executeUpdate()
//    }
//
//    override fun subtractStorageCount(storageDTO: UpdateStorageDTO) {
//        if (notHaveDataForUpdate(storageDTO)) {
//            throw NoResultException("Não há registro de estoque para o Produto e/ou Marca especificado(s)")
//        }
//
//        if (newStorageCountIsInvalid(storageDTO)) {
//            throw InvalidStorageOperationException("A quantidade a ser reduzida é maior do que a quantidade em estoque.")
//        }
//
//        val params = mutableListOf<Parameter>()
//        val sql = StringJoiner("\n\t")
//
//        with(sql) {
//            add("UPDATE ${ProductBrand::class.java.name}")
//            add("SET count = count - :pCount")
//            add("WHERE product.id = :pProductId AND brand.id = :pBrandId")
//        }
//
//        params.add(Parameter("pCount", storageDTO.count))
//        params.add(Parameter("pProductId", storageDTO.productId))
//        params.add(Parameter("pBrandId", storageDTO.brandId))
//
//        val query = entityManager.createQuery(sql.toString())
//        query.setParameters(params)
//
//        query.executeUpdate()
//    }
//
//    private fun newStorageCountIsInvalid(storageDTO: UpdateStorageDTO): Boolean {
//        val params = mutableListOf<Parameter>()
//        val sql = StringJoiner("\n\t")
//
//        with(sql) {
//            add("SELECT p.count < :pCount as valid")
//            add("FROM ${ProductBrand::class.java.name} p")
//            add("WHERE product.id = :pProductId AND brand.id = :pBrandId")
//        }
//
//        params.add(Parameter("pCount", storageDTO.count))
//        params.add(Parameter("pProductId", storageDTO.productId))
//        params.add(Parameter("pBrandId", storageDTO.brandId))
//
//        val query = entityManager.createQuery(sql.toString(), Boolean::class.java)
//        query.setParameters(params)
//
//        return query.singleResult
//    }
//
//    private fun notHaveDataForUpdate(storageDTO: UpdateStorageDTO): Boolean {
//        val params = mutableListOf<Parameter>()
//        val sql = StringJoiner("\n\t")
//
//        with(sql) {
//            add("select (")
//            add("        not exists(select pb.id from products_brands pb where pb.product_id = :pProductId and pb.brand_id = :pBrandId)")
//            add("       )")
//        }
//
//        params.add(Parameter("pProductId", storageDTO.productId))
//        params.add(Parameter("pBrandId", storageDTO.brandId))
//
//        val query = entityManager.createNativeQuery(sql.toString(), Boolean::class.java)
//        query.setParameters(params)
//
//        return query.singleResult as Boolean
//    }
}