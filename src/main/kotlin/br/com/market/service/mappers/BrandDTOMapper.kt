package br.com.market.service.mappers

import br.com.market.service.dto.NewBrandDTO
import br.com.market.service.models.Brand

class BrandDTOMapper : Mapper<Brand, NewBrandDTO> {

    override fun map(value: Brand): NewBrandDTO {
        return NewBrandDTO(id = value.id, name = value.name)
    }

}