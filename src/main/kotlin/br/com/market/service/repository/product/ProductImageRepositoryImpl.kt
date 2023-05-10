package br.com.market.service.repository.product

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.ProductImage
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import java.util.*

class ProductImageRepositoryImpl : ICustomProductImageRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findProductImageByLocalId(localId: UUID): ProductImage? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT pi")
            add("FROM ${ProductImage::class.java.name} pi ")
            add("WHERE pi.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localId))

        val query = entityManager.createQuery(sql.toString(), ProductImage::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun deleteProductImagesByProductLocalId(localProductId: UUID) {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("delete from ${ProductImage::class.java.name} pi")
            add("where pi.product.localId = :pLocalProductId")
        }

        params.add(Parameter(name = "pLocalProductId", value = localProductId))

        val query = entityManager.createQuery(sql.toString())
        query.setParameters(params)

        query.executeUpdate()
    }

}