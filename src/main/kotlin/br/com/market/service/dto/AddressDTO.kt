package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO

data class AddressDTO(
    override var localId: String,
    override var id: Long? = null,
    override var active: Boolean = true,
    var state: String? = null,
    var city: String? = null,
    var publicPlace: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var cep: String? = null
): MobileDTO()
