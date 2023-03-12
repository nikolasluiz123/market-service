package br.com.market.service.controller

import br.com.market.service.dto.brand.DeleteBrandDTO
import br.com.market.service.dto.brand.NewBrandDTO
import br.com.market.service.dto.brand.UpdateBrandDTO
import br.com.market.service.dto.product.DeleteProductDTO
import br.com.market.service.response.MarketServiceResponse
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.service.BrandService
import jakarta.validation.Valid
import org.hibernate.sql.Delete
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brand")
class BrandController(private val service: BrandService) {

    @PostMapping
    @Transactional(timeout = 600)
    fun saveBrand(@RequestBody @Valid newBrandDTO: NewBrandDTO): ResponseEntity<PersistenceResponse> {
        val savedBrand = service.saveBrand(newBrandDTO)

        return savedBrand?.let {
            ResponseEntity.ok(
                PersistenceResponse(
                    idRemote = it.id,
                    code = HttpStatus.OK.value(),
                    success = true
                )
            )
        } ?: ResponseEntity.ok(
            PersistenceResponse(
                code = HttpStatus.NOT_FOUND.value(),
                error = "Não foi possível encontrar o produto para realizar o cadastro da marca, provavelmente ele foi removido em outra operação."
            )
        )
    }

    @PutMapping
    @Transactional(timeout = 600)
    fun updateBrand(@RequestBody @Valid updateBrandDTO: UpdateBrandDTO): ResponseEntity<PersistenceResponse> {
        val updatedBrand = service.updateBrand(updateBrandDTO)

        return updatedBrand?.let {
            ResponseEntity.ok(
                PersistenceResponse(
                    idRemote = it.id,
                    code = HttpStatus.OK.value(),
                    success = true
                )
            )
        } ?: ResponseEntity.ok(
            PersistenceResponse(
                code = HttpStatus.NOT_FOUND.value(),
                error = "Não foi possível encontrar a marca para realizar a atualização, provavelmente ela foi removida em outra operação."
            )
        )
    }

    @PostMapping("/delete")
    @Transactional(timeout = 600)
    fun deleteBrand(@RequestBody deleteBrandDTO: DeleteBrandDTO): ResponseEntity<MarketServiceResponse> {
        service.deleteBrand(deleteBrandDTO)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/synchronize/delete")
    @Transactional(rollbackFor = [Exception::class], timeout = 600)
    fun deleteBrands(@RequestBody brandDTOs: List<DeleteBrandDTO>): ResponseEntity<MarketServiceResponse> {
        service.deleteBrands(brandDTOs)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/synchronize")
    @Transactional(rollbackFor = [Exception::class], timeout = 600)
    fun syncProducts(@RequestBody @Valid brandsDTO: List<NewBrandDTO>): ResponseEntity<MarketServiceResponse> {
        service.syncBrands(brandsDTO)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

}