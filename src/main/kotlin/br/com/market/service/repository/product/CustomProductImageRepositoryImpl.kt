package br.com.market.service.repository.product

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.ProductImage
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomProductImageRepositoryImpl : ICustomProductImageRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findProductImageByLocalId(localId: String): ProductImage? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT pi")
            add("FROM ${ProductImage::class.java.name} pi ")
            add("WHERE pi.localId = :pIdLocal")
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

    override fun findProductImagesByProductId(productId: Long): List<ProductImage> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT pi")
            add("FROM ${ProductImage::class.java.name} pi ")
            add("WHERE pi.product.id = :pProductId")
        }

        params.add(Parameter(name = "pProductId", value = productId))

        val query = entityManager.createQuery(sql.toString(), ProductImage::class.java)
        query.setParameters(params)

        return query.resultList
    }

    override fun toggleActiveProductImagesByProductLocalId(localProductId: String, active: Boolean) {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("update ${ProductImage::class.java.name} pi set active = :pActive")
            add("where pi.id in (")
            add("                   select image.id")
            add("                   from ${ProductImage::class.java.name} image")
            add("                   inner join image.product p")
            add("                   where p.localId = :pLocalProductId")
            add("              )")
        }

        params.add(Parameter(name = "pLocalProductId", value = localProductId))
        params.add(Parameter(name = "pActive", value = active))

        val query = entityManager.createQuery(sql.toString())
        query.setParameters(params)

        query.executeUpdate()
    }

    override fun updateProductImagePrincipal(productId: Long, id: Long) {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("update ${ProductImage::class.java.name} pi")
            add("set pi.principal = false")
            add("where pi.product.id = :pProductId")
            add("and pi.id != :pProductImageId")
        }

        params.add(Parameter(name = "pProductId", value = productId))
        params.add(Parameter(name = "pProductImageId", value = id))

        val query = entityManager.createQuery(sql.toString())
        query.setParameters(params)

        query.executeUpdate()
    }

    override fun findAll(marketId: Long, limit: Int?, offset: Int?): List<ProductImage> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT pi")
            add("FROM ${ProductImage::class.java.name} pi ")
            add("WHERE pi.market.id = :pMarketId")
        }

        params.add(Parameter(name = "pMarketId", value = marketId))

        val query = entityManager.createQuery(sql.toString(), ProductImage::class.java)
        query.setParameters(params)
        query.maxResults = limit ?: Int.MAX_VALUE
        query.firstResult = offset ?: 0

        return query.resultList
    }

    override fun toggleActiveProductImage(productId: String, imageId: String) {
        val images = getProductImages(productId)
        val imageToToggleActive = images.find { it.localId == imageId }!!
        images.remove(imageToToggleActive)

        if (imageToToggleActive.principal!!) {
            val image = images[0]
            image.principal = true

            updateProductImagePrincipal(image.product?.id!!, image.id!!)
            inactivateImage(imageId)
        }
    }

    private fun getProductImages(productId: String): MutableList<ProductImage> {
        val params = mutableListOf<Parameter>()
        params.add(Parameter("pProductId", productId))

        val select = StringJoiner("\n\t")
        with(select) {
            add(" select image.* ")
            add(" from ${ProductImage::class.java.name} image ")
            add(" where image.product.id = :pProductId and image.active ")
        }

        val query = entityManager.createQuery(select.toString(), ProductImage::class.java)
        query.setParameters(params)

        return query.resultList
    }

    private fun inactivateImage(imageId: String) {
        val params = mutableListOf<Parameter>()
        params.add(Parameter(name = "pImageId", value = imageId))

        val sql = StringJoiner("\n\t")

        with(sql) {
            add("update ${ProductImage::class.java.name} pi")
            add("set pi.active = false")
            add("where pi.localId = :pImageId")
        }

        val query = entityManager.createQuery(sql.toString())
        query.setParameters(params)

        query.executeUpdate()
    }

}