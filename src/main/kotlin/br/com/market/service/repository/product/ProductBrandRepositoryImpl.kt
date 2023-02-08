package br.com.market.service.repository.product

import br.com.market.service.dto.brand.UpdateStorageDTO
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.ProductBrand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.util.StringJoiner

class ProductBrandRepositoryImpl : CustomProductBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findByProductId(id: Long): List<ProductBrand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${ProductBrand::class.java.name} p ")
            add("WHERE p.product.id = :pProductId")
        }

        params.add(Parameter(name = "pProductId", value = id))

        val query = entityManager.createQuery(sql.toString(), ProductBrand::class.java)
        query.setParameters(params)

        return query.resultList
    }

    override fun sumStorageCount(storageDTO: UpdateStorageDTO) {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("UPDATE ${ProductBrand::class.java.name}")
            add("SET count = count + :pCount")
            add("WHERE product.id = :pProductId AND brand.id = :pBrandId")
        }

        params.add(Parameter("pCount", storageDTO.count))
        params.add(Parameter("pProductId", storageDTO.productId))
        params.add(Parameter("pBrandId", storageDTO.brandId))

        val query = entityManager.createQuery(sql.toString())
        query.setParameters(params)

        query.executeUpdate()
    }

    override fun subtractStorageCount(storageDTO: UpdateStorageDTO) {
        if (newStorageCountIsInvalid(storageDTO)) {
            throw IllegalArgumentException("A quantidade a ser reduzida é maior do que a quantidade em estoque.")
        }

        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("UPDATE ${ProductBrand::class.java.name}")
            add("SET count = count - :pCount")
            add("WHERE product.id = :pProductId AND brand.id = :pBrandId")
        }

        params.add(Parameter("pCount", storageDTO.count))
        params.add(Parameter("pProductId", storageDTO.productId))
        params.add(Parameter("pBrandId", storageDTO.brandId))

        val query = entityManager.createQuery(sql.toString())
        query.setParameters(params)

        query.executeUpdate()
    }

    private fun newStorageCountIsInvalid(storageDTO: UpdateStorageDTO): Boolean {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p.count < :pCount as valid")
            add("FROM ${ProductBrand::class.java.name} p")
            add("WHERE product.id = :pProductId AND brand.id = :pBrandId")
        }

        params.add(Parameter("pCount", storageDTO.count))
        params.add(Parameter("pProductId", storageDTO.productId))
        params.add(Parameter("pBrandId", storageDTO.brandId))

        val query = entityManager.createQuery(sql.toString(), Boolean::class.java)
        query.setParameters(params)

        return query.singleResult
    }
}