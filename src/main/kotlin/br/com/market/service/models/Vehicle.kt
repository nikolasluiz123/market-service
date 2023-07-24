package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketModel
import br.com.market.service.models.enumeration.EnumVehicleType
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos veículos.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "vehicles")
data class Vehicle(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long?,
    override var active: Boolean,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String?,
    var type: EnumVehicleType? = null,
): MobileMarketModel()