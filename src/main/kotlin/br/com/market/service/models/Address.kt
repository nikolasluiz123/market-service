package br.com.market.service.models

import br.com.market.service.models.base.BaseModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos endereços.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "addresses")
data class Address(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @Column(name = "local_id")
    var localId: String? = null,
    var state: String? = null,
    var city: String? = null,
    @Column(name = "public_place")
    var publicPlace: String? = null,
    var number: String? = null,
    var complement: String? = null,
    @Column(length = 9)
    var cep: String? = null
) : BaseModel()