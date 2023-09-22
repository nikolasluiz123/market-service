package br.com.market.service.repository.user

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.User
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomUserRepositoryImpl: ICustomUserRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findUserByLocalId(localId: String): User? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT u")
            add("FROM ${User::class.java.name} u ")
            add("WHERE u.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), User::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findAll(marketId: Long, limit: Int?, offset: Int?): List<User> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT u")
            add("FROM ${User::class.java.name} u ")
            add("WHERE u.market.id = :pMarketId")
        }

        params.add(Parameter(name = "pMarketId", value = marketId))

        val query = entityManager.createQuery(sql.toString(), User::class.java)
        query.setParameters(params)
        query.maxResults = limit ?: Int.MAX_VALUE
        query.firstResult = offset ?: 0

        return query.resultList
    }

    override fun isUniqueEmail(email: String): Boolean {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add(" SELECT EXISTS ( ")
            add("                   SELECT 1 ")
            add("                   FROM ${User::class.java.name} u ")
            add("                   WHERE u.email = :pEmail ")
            add("               )  as unique")
        }

        params.add(Parameter(name = "pEmail", value = email))

        val query = entityManager.createQuery(sql.toString(), Boolean::class.java)
        query.setParameters(params)

        return try {
            !query.singleResult
        } catch (e: NoResultException) {
            return true
        }
    }
}