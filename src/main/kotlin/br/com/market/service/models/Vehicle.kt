package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import br.com.market.service.models.enumeration.EnumVehicleType
import jakarta.persistence.*
import java.util.*

@Entity(name = "vehicles")
data class Vehicle(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long?,
    override var active: Boolean,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company?,
    @Column(name = "local_id")
    override var localId: UUID?,
    var type: EnumVehicleType? = null,
): MobileCompanyModel()