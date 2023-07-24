package br.com.market.service.repository.market

import br.com.market.service.models.Market
import org.springframework.data.jpa.repository.JpaRepository


interface IMarketRepository : JpaRepository<Market, Long>