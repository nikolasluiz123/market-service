package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO

data class UserDTO(
    override var localId: String,
    override var id: Long? = null,
    override var active: Boolean = true,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var token: String? = null,
    var marketId: Long? = null
): MobileDTO()
