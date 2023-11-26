package br.com.market.service.service

import br.com.market.service.dto.ClientDTO
import br.com.market.service.exeption.BusinessException
import br.com.market.service.models.Address
import br.com.market.service.models.Client
import br.com.market.service.repository.address.IAddressRepository
import br.com.market.service.repository.address.ICustomAddressRepository
import br.com.market.service.repository.client.IClientRepository
import br.com.market.service.repository.client.ICustomClientRepository
import br.com.market.service.repository.user.ICustomUserRepository
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.util.CEPUtil
import br.com.market.service.util.CPFUtil
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ClientService(
    var clientRepository: IClientRepository,
    var customClientRepository: ICustomClientRepository,
    var addressRepository: IAddressRepository,
    var customAddressRepository: ICustomAddressRepository,
    var customUserRepository: ICustomUserRepository
) {

    fun save(dto: ClientDTO): PersistenceResponse {
        validateClient(dto)

        val client = customClientRepository.findClientByLocalId(dto.localId)
        val address = customAddressRepository.findAddressByLocalId(dto.address.localId)
        val user = customUserRepository.findUserByLocalId(dto.user.localId)!!

        if (client != null) {
            val clientWithNewInfo = client.copy(
                user = user,
                address = address!!.copy(
                    cep = dto.address.cep,
                    state = dto.address.state,
                    city = dto.address.city,
                    publicPlace = dto.address.publicPlace,
                    complement = dto.address.complement,
                    number = dto.address.number
                ),
                cpf = dto.cpf
            )

            clientRepository.save(clientWithNewInfo)
        } else {
            val newAddress = dto.address.run {
                Address(
                    cep = cep,
                    state = state,
                    city = city,
                    publicPlace = publicPlace,
                    id = id,
                    localId = localId,
                    active = active,
                    number = number,
                    complement = complement
                )
            }
            val newClient = dto.run {
                Client(
                    user = user,
                    address = newAddress,
                    cpf = cpf,
                    localId = localId
                )
            }

            addressRepository.save(newAddress)
            clientRepository.save(newClient)
        }

        return PersistenceResponse(
            code = HttpStatus.OK.value(),
            success = true
        )
    }

    @Throws(BusinessException::class)
    private fun validateClient(dto: ClientDTO) {
        when {
            dto.cpf.isEmpty() -> throw BusinessException("O cpf é obrigatório.")
            !CPFUtil.isValid(dto.cpf) -> throw BusinessException("O cpf é inválido.")
            !isUniqueCPF(dto.cpf) -> throw BusinessException("CPF já cadastrado.")

            dto.address.cep.isNullOrEmpty() -> throw BusinessException("O CEP é obrigatório.")
            !CEPUtil.isValid(dto.address.cep!!) -> throw BusinessException("O CEP é inválido.")

            dto.address.state.isNullOrEmpty() -> throw BusinessException("O estado é obrigatório.")
            dto.address.state!!.length > 255 -> throw BusinessException("O estado deve conter menos de 255 caractéres.")

            dto.address.city.isNullOrEmpty() -> throw BusinessException("A cidade é obrigatória.")
            dto.address.city!!.length > 255 -> throw BusinessException("A cidade deve conter menos de 255 caractéres.")

            dto.address.publicPlace.isNullOrEmpty() -> throw BusinessException("O logradouro é obrigatório.")
            dto.address.publicPlace!!.length > 255 -> throw BusinessException("O logradouro deve conter menos de 255 caractéres.")

            dto.address.number.isNullOrEmpty() -> throw BusinessException("O número é obrigatório.")
            dto.address.number!!.length > 255 -> throw BusinessException("O número deve conter menos de 255 caractéres.")
        }
    }

    fun isUniqueEmail(email: String): Boolean = customUserRepository.isUniqueEmail(email)

    fun isUniqueCPF(cpf: String): Boolean = customClientRepository.isUniqueCPF(cpf)
}