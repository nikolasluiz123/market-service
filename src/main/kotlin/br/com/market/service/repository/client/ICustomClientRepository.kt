package br.com.market.service.repository.client

import br.com.market.service.models.Client

interface ICustomClientRepository {

    fun findClientByLocalId(localId: String): Client?

    fun isUniqueCPF(cpf: String): Boolean

}