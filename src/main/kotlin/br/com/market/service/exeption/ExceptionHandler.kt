package br.com.market.service.exeption

import br.com.market.service.response.PersistenceResponse
import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.hibernate.TransactionException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(TransactionException::class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    fun handlerServiceTimeout(
        exception: TransactionException,
        request: HttpServletRequest
    ): PersistenceResponse {
        return PersistenceResponse(
            code = HttpStatus.REQUEST_TIMEOUT.value(),
            error = "A requisição excedeu o tempo de 10 minutos e a conexão com o serviço foi desfeita."
        )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerEntityNotFound(
        exception: EntityNotFoundException,
        request: HttpServletRequest
    ): PersistenceResponse {
        return PersistenceResponse(
            code = HttpStatus.NOT_FOUND.value(),
            error = exception.message
        )
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerValidationExceptions(exception: BusinessException): PersistenceResponse {
        return PersistenceResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            error = exception.message
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerExceptions(exception: Exception): PersistenceResponse {
        exception.printStackTrace()

        return PersistenceResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            error = exception.message ?: "Ocorreu um erro não tratado."
        )
    }
}