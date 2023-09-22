package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketRestrictionModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos entregadores.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "delivery_mans")
data class DeliveryMan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    @OneToOne @JoinColumn(name = "user_id")
    var user: User? = null,
    @ManyToOne @JoinColumn(name = "vehicle_id")
    var vehicle: Vehicle? = null
): MobileMarketRestrictionModel()