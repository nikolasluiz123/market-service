package br.com.market.service.service

import br.com.market.service.dto.ViaCepDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(url= "https://viacep.com.br/ws/" , name = "viacep")
interface ViaCepClient {

    @GetMapping("{cep}/json")
    fun getAddressByCep(@PathVariable("cep") cep: String): ViaCepDTO?
}