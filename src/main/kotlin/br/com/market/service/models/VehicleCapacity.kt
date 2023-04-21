package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import br.com.market.service.models.enumeration.EnumUnit
import jakarta.persistence.*
import java.util.*

@Entity(name = "vehicles_capacities")
data class VehicleCapacity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: UUID? = null,
    var capacity: Int = 0,
    @Column(name = "capacity_unit")
    var capacityUnit: EnumUnit? = null,
    @ManyToOne @JoinColumn(name = "vehicle_id")
    var vehicle: Vehicle? = null
) : MobileCompanyModel()