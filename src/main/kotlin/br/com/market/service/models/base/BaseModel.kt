package br.com.market.service.models.base

/**
 * Classe abstrata que obriga a existência dos
 * atributos padrões de toda entidade.
 *
 * @author Nikolas Luiz Schmitt
 */
abstract class BaseModel {
    abstract var id: Long?
    abstract var active: Boolean
}