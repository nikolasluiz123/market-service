package br.com.market.service.repository.market

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Device
import br.com.market.service.models.Market
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
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

}