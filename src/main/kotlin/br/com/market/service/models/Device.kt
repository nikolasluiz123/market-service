package br.com.market.service.models

import br.com.market.service.models.base.CompanyModel
import jakarta.persistence.*

@Entity(name = "devices")
data class Device(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    var name: String? = null,
    var imei: String? = null
): CompanyModel()
