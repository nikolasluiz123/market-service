package br.com.market.service.dto.storageoperationhistory

import br.com.market.service.dto.base.MobileDTO
import br.com.market.service.models.enumeration.EnumOperationType
import java.time.LocalDateTime

data class StorageOperationHistoryDTO(
    override var localId: String,
    override var id: Long? = null,
    override var active: Boolean = true,
    var companyId: Long? = null,
    var productId: String? = null,
    var quantity: Int = 0,
    var dateRealization: LocalDateTime? = null,
    var datePrevision: LocalDateTime? = null,
    var operationType: EnumOperationType? = null,
    var description: String? = null,
    var userId: String? = null
): MobileDTO()
