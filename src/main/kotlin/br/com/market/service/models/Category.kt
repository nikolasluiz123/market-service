package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import jakarta.persistence.*
import java.util.*

@Entity(name = "categories")
data class Category(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: UUID? = null,
    var name: String? = null
) : MobileCompanyModel()