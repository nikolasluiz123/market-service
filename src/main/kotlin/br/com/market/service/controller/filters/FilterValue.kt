package br.com.market.service.controller.filters

open class FilterValue<T>(var checked: Boolean = false, var value: T? = null) {

    open fun isFilterApplied(): Boolean {
        return this.checked && this.value != null
    }
}