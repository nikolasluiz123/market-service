package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketModel
import br.com.market.service.models.enumeration.EnumOperationType
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Classe que representa a tabela das operações de estoque realizadas.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "storage_operations_history")
data class StorageOperationHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    @Column(name = "date_realization")
    var dateRealization: LocalDateTime? = null,
    @Column(name = "date_prevision")
    var datePrevision: LocalDateTime? = null,
    @Column(name = "operation_type")
    var operationType: EnumOperationType? = null,
    var description: String? = null,
    @ManyToOne @JoinColumn(name = "product_id")
    var product: Product? = null,
    var quantity: Int? = null,
    @ManyToOne @JoinColumn(name = "user_id")
    var user: User? = null
): MobileMarketModel()