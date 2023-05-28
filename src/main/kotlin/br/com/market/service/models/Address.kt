package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import jakarta.persistence.*

@Entity(name = "addresses")
data class Address(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var state: String? = null,
    var city: String? = null,
    @Column(name = "public_place")
    var publicPlace: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var cep: String? = null
) : MobileCompanyModel()