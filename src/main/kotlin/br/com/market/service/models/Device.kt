package br.com.market.service.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * Classe que representa a tabela dos dispositivos.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "devices")
data class Device(
    @Id
    var id: String? = null,
    var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    var market: Market? = null,
    var name: String? = null,
)
