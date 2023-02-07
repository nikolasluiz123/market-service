package br.com.market.service.mappers

import br.com.market.service.dto.BrandDTO
import br.com.market.service.models.Brand

class BrandDTOMapper : Mapper<Brand, BrandDTO> {

    override fun map(value: Brand): BrandDTO {
        return BrandDTO(id = value.id, name = value.name)
    }

}