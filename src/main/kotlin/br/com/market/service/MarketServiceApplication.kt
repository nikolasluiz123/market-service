package br.com.market.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MarketServiceApplication

fun main(args: Array<String>) {
	runApplication<MarketServiceApplication>(*args)
}
