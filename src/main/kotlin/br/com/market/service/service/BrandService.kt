package br.com.market.service.service

import br.com.market.service.repository.brand.BrandRepository
import org.springframework.stereotype.Service

@Service
class BrandService(private val brandRepository: BrandRepository) {
}