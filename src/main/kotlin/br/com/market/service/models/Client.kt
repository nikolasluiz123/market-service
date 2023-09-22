package br.com.market.service.models

import br.com.market.service.models.base.MobileModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos clientes.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "clients")
data class Client(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @Column(name = "local_id")
    override var localId: String? = null,
    @Column(length = 14)
    val cpf: String? = null,
    @OneToOne @JoinColumn(name = "user_id")
    var user: User? = null,
    @OneToOne @JoinColumn(name = "address_id")
    var address: Address? = null,
): MobileModel()
