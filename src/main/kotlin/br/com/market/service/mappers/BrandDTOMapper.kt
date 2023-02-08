package br.com.market.service.mappers

import br.com.market.service.dto.NewBrandDTO
import br.com.market.service.models.Brand

object BrandDTOMapper : Mapper<Brand, NewBrandDTO> {

    override fun toDTO(value: Brand)= NewBrandDTO(id = value.id, name = value.name)

    override fun toModel(value: NewBrandDTO) = Brand(id = value.id, name = value.name)

}