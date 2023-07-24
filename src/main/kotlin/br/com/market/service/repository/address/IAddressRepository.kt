package br.com.market.service.repository.address

import br.com.market.service.models.Address
import org.springframework.data.jpa.repository.JpaRepository


interface IAddressRepository : JpaRepository<Address, Long>