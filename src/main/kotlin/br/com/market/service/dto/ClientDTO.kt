package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO

data class ClientDTO(
    override var id: Long?,
    override var active: Boolean,
    override var localId: String,
    var user: UserDTO,
    var address: AddressDTO,
    var cpf: String
): MobileDTO()