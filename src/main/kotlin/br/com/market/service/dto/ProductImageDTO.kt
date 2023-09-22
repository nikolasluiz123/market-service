package br.com.market.service.dto

import br.com.market.service.dto.base.MobileDTO

data class ProductImageDTO(
    override var localId: String,
    override var active: Boolean = true,
    override var id: Long? = null,
    var bytes: ByteArray? = null,
    var imageUrl: String? = null,
    var productLocalId: String? = null,
    var principal: Boolean = false,
    var marketId: Long? = null
) : MobileDTO() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductImageDTO

        if (localId != other.localId) return false

        return true
    }

    override fun hashCode(): Int {
        return localId.hashCode()
    }
}