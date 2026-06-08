package com.victordionizio.supercompras

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.plus

class SuperComprasViewModel : ViewModel() {
    private val _listaDeItens  = MutableStateFlow<List<ItemCompra>>(emptyList())
    val listaDeItens : StateFlow<List<ItemCompra>> = _listaDeItens

    fun adicionarItem(item: ItemCompra) {
        _listaDeItens.update { lista ->
            lista + item
        }
        listaDeItens = listaDeItens + novoItem
    }
}