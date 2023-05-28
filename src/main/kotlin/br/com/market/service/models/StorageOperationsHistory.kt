package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import br.com.market.service.models.enumeration.EnumOperationType
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity(name = "storage_operations_history")
data class StorageOperationsHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    @Column(name = "date_realization")
    var dateRealization: LocalDateTime = LocalDateTime.now(),
    @Column(name = "date_prevision")
    var datePrevision: LocalDateTime = LocalDateTime.now(),
    @Column(name = "operation_type")
    var operationType: EnumOperationType? = null,
    var description: String? = null,
    @ManyToOne @JoinColumn(name = "storage_product_id")
    var storageProduct: StorageProduct? = null,
    @ManyToOne @JoinColumn(name = "user_id")
    var user: User? = null
): MobileCompanyModel()