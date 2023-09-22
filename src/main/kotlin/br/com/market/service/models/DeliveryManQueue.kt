package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketRestrictionModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela da fila dos entregadores.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "delivery_mans_queue")
data class DeliveryManQueue(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    @Column(name = "delivery_started")
    var deliveryStarted: Boolean = false,
    @OneToOne @JoinColumn(name = "purchase_cart_id")
    var purchaseCart: PurchaseCart? = null
): MobileMarketRestrictionModel()