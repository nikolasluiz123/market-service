package br.com.market.service.repository.user

import br.com.market.service.models.User

interface ICustomUserRepository {

    fun findUserByLocalId(localId: String): User?

    fun findAll(marketId: Long, limit: Int? = null, offset: Int? = null): List<User>

    fun isUniqueEmail(email: String): Boolean
}