package br.com.market.service.repository.client

import br.com.market.service.models.Client
import org.springframework.data.jpa.repository.JpaRepository

interface IClientRepository: JpaRepository<Client, Long>