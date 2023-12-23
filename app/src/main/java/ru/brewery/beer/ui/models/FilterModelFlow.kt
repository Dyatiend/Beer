package ru.brewery.beer.ui.models

import kotlinx.coroutines.flow.MutableStateFlow
import ru.brewery.beer.data.domain.Order
import ru.brewery.beer.data.domain.Sort

data class FilterModelFlow(
    val query : MutableStateFlow<String?> = MutableStateFlow(null),
    val styles : MutableStateFlow<List<Int>?> = MutableStateFlow(null),
    val abvMin : MutableStateFlow<Double?> = MutableStateFlow(null),
    val abvMax : MutableStateFlow<Double?> = MutableStateFlow(null),
    val ibuMin : MutableStateFlow<Double?> = MutableStateFlow(null),
    val ibuMax : MutableStateFlow<Double?> = MutableStateFlow(null),
    val isOrganic : MutableStateFlow<Boolean?> = MutableStateFlow(null),
    val isRetired : MutableStateFlow<Boolean?> = MutableStateFlow(null),
    val sort : MutableStateFlow<Sort?> = MutableStateFlow(null),
    val order : MutableStateFlow<Order?> = MutableStateFlow(null)
) {

    fun setQuery(q : String?) {
        query.value = q
    }
    fun setStyles(l : List<Int>?) {
        styles.value = l
    }
    fun setAbvMin(am : Double?) {
        abvMin.value = am
    }
    fun setAbvMax(am : Double?) {
        abvMax.value = am
    }
    fun setIbuMin(im : Double?) {
        abvMin.value = im
    }
    fun setIbuMax(im : Double?) {
        abvMin.value = im
    }
    fun setIsOrganic(o : Boolean?) {
        isOrganic.value = o
    }
    fun setIsRetired(r : Boolean?) {
        isRetired.value = r
    }
    fun setSort(s : Sort?) {
        sort.value = s
    }
    fun setOrder(o : Order) {
        order.value = o
    }

}