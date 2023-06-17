package br.com.market.service.repository.user

import br.com.market.service.models.User

interface ICustomUserRepository {

    fun findUserByLocalId(localId: String): User?
}