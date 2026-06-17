package com.victordionizio.supercompras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map
import kotlin.collections.plus

class SuperComprasViewModel : ViewModel() {
    // _listaDeItens é um MutableStateFlow que armazena a lista de itens de compra. Ele é privado para garantir que apenas a ViewModel possa modificá-lo.
    // MutableStateFlow é uma classe do Kotlin que representa um fluxo de dados mutável. Ele permite que a ViewModel atualize a lista de itens e notifique os observadores sobre as mudanças.
    private val _listaDeItens  = MutableStateFlow<List<ItemCompra>>(emptyList())
    val listaDeItens : StateFlow<List<ItemCompra>> = _listaDeItens

    fun adicionarItem(item: ItemCompra) {
        // viewModelScope é uma extensão da classe ViewModel que fornece um escopo de corrotina ligado ao ciclo de vida da ViewModel. Isso significa que as corrotinas iniciadas dentro desse escopo serão automaticamente canceladas quando a ViewModel for destruída, evitando vazamentos de memória.
        viewModelScope.launch {
            _listaDeItens.update { lista ->
                lista + item
            }
        }
    }

    fun removerItem(item: ItemCompra) {
        // viewModelScope.launch é usado para iniciar uma nova corrotina dentro do escopo da ViewModel. Isso garante que as operações de atualização da lista sejam realizadas de forma assíncrona, evitando bloqueios na interface do usuário.
        viewModelScope.launch {
            _listaDeItens.update { lista ->
                lista - item
            }
        }
    }

    fun editarItem(itemEditado: ItemCompra, novoTexto: String) {
        viewModelScope.launch {
            _listaDeItens.update { lista ->
                lista.map { itemAtual ->
                    if (itemAtual == itemEditado) {
                        itemAtual.copy(texto = novoTexto)
                    } else {
                        itemAtual
                    }
                }
            }
        }

    }

    fun mudarStatus(itemSelecionado : ItemCompra){
        viewModelScope.launch {
            _listaDeItens.update { lista ->
                lista.map { itemMap ->

                    // 2. Verificamos se o ‘item’ que estamos a percorrer agora no map
                    // é exatamente o ‘item’ que o usuário clicou
                    if (itemSelecionado == itemMap) {

                        // 3. Se for o item clicado, usamos o .copy() para criar um NOVO objeto.
                        // Ele cria um novo item identico, mudando APENAS o que você colocar no parênteses
                        // Se era true, vira false. Se era false, vira true (!).

                        itemSelecionado.copy(foiComprado = !itemSelecionado.foiComprado)

                    } else {
                        // 4. Se não for o item clicado, retornamos ele sem nenhuma alteração
                        itemMap
                    }
                }
            }
        }
    }
}