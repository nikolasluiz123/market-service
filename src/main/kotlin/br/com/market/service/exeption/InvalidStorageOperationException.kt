package br.com.market.service.exeption

import java.lang.RuntimeException

class InvalidStorageOperationException(message: String?) : RuntimeException(message)