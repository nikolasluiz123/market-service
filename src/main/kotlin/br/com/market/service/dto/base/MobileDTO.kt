package br.com.market.service.dto.base

import java.util.*

abstract class MobileDTO : BaseDTO() {
    abstract var localId: UUID
}