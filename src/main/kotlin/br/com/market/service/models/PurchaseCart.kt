package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketRestrictionModel
import br.com.market.service.models.enumeration.EnumPaymentType
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Classe que representa a tabela dos carrinhos de compra.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "purchases_carts")
data class PurchaseCart(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var date: LocalDateTime = LocalDateTime.now(),
    @Column(name = "payment_type")
    var paymentType: EnumPaymentType? = null,
    var delivery: Boolean = false,
    @ManyToOne @JoinColumn(name = "client_id")
    var client: Client? = null
) : MobileMarketRestrictionModel()