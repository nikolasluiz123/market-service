package br.com.market.service.service

import br.com.market.service.dto.brand.BrandDTO
import br.com.market.service.models.Brand
import br.com.market.service.repository.brand.IBrandRepository
import org.springframework.stereotype.Service

@Service
class BrandService(private val repository: IBrandRepository) {
    
    fun save(dto: BrandDTO) {
        val brand = repository.findBrandByLocalId(dto.localBrandId)?.copy(
            name = dto.name,
            localId = dto.localBrandId,
            active = dto.active
        ) ?: Brand(
            name = dto.name,
            localId = dto.localBrandId,
            active = dto.active
        )

        repository.save(brand)
    }

    fun toggleActive(categoryDTO: BrandDTO) {
        repository.findBrandByLocalId(categoryDTO.localBrandId)?.let {
            repository.save(it.copy(active = !it.active))
        }
    }

    fun sync(categoriesDTOs: List<BrandDTO>) {
        categoriesDTOs.forEach(::save)
    }

    fun findAll(): List<BrandDTO> {
        return repository.findAll().map {
            BrandDTO(
                localBrandId = it.localId!!,
                name = it.name,
                companyId = it.company?.id,
                active = it.active
            )
        }
    }
}