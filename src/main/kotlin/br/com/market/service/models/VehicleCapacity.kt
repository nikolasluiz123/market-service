package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketRestrictionModel
import br.com.market.service.models.enumeration.EnumUnit
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos capacidades dos veículos.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "vehicles_capacities")
data class VehicleCapacity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var capacity: Int = 0,
    @Column(name = "capacity_unit")
    var capacityUnit: EnumUnit? = null,
    @ManyToOne @JoinColumn(name = "vehicle_id")
    var vehicle: Vehicle? = null
) : MobileMarketRestrictionModel()