package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity(name = "cards")
data class Card(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: UUID? = null,
    var accaount: String? = null,
    var agency: String? = null,
    var bank: String? = null,
    var validity: LocalDate? = null,
    @ManyToOne @JoinColumn(name = "client_id")
    var client: Client? = null,
) : MobileCompanyModel()