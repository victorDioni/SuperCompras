package com.victordionizio.supercompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victordionizio.supercompras.ui.theme.Marinho
import com.victordionizio.supercompras.ui.theme.SuperComprasTheme
import com.victordionizio.supercompras.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperComprasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaDeCompras(Modifier.padding(innerPadding))
                }
            }
        }
    }


    @Composable
    fun ListaDeCompras(modifier: Modifier = Modifier) {
        var listaDeItens by rememberSaveable { mutableStateOf(listOf<ItemCompra>()) }
        LazyColumn( // LazyColumn é um componente de layout que exibe uma lista rolável de itens, onde os itens são carregados de forma preguiçosa (lazy loading) à medida que o usuário rola a lista.
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally, // Alinha os itens horizontalmente ao centro
            modifier = modifier
        ) {
            item {
                ImagemTopo()
                AdicionarItem(aoSalvarItem = { novoItem -> // Ação a ser executada quando o usuário clicar no botão "Salvar item" e passar o texto digitado como argumento
                    listaDeItens =
                        listaDeItens + novoItem // Cria um novo item de compra com o texto digitado e adiciona à lista de itens usando o operador +, que cria uma nova lista com o novo item adicionado.
                })
                Spacer(modifier = Modifier.height(48.dp)) // Adiciona um espaçamento entre o botão e o título
                Titulo(texto = "Lista de Compras")
            }

            ListaDeItens(
                lista = listaDeItens.filter { !it.foiComprado },
                aoMudarStatus = { itemSelecionado ->
                    /*
                    1. Entrada: Lista de itens (ex: Arroz, Feijão, Batata).
                    2. Ação do Map: Ele percorre a lista.
                    3. A Transformação:
                        "Você é o ‘item’ que eu cliquei?"
                        Se SIM: Eu te transformo numa versão "marcada como comprado".
                        Se NÃO: Eu te mantenho exatamente como você era.
                    4. Saída: Uma nova lista onde todos os itens continuam lá, mas aquele que você clicou está transformado.
                    * */
                    // 1. Criamos uma NOVA lista baseada na antiga usando o.map
                    listaDeItens = listaDeItens.map { itemMap ->

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
                },
                aoRemoverItem = { itemRemovido ->
                    listaDeItens = listaDeItens - itemRemovido

                },
                aoEditarItem = { itemEditado, novoTexto ->
                    listaDeItens = listaDeItens.map { itemAtual ->
                        if (itemAtual == itemEditado) {
                            itemAtual.copy(texto = novoTexto)
                        } else {
                            itemAtual
                        }
                    }
                }
            )

            item {
                Titulo(texto = "Comprados")
            }

            // Verifica se há algum item marcado como comprado usando o
            // métodoo any, que retorna true se pelo menos um item na lista atender à condição especificada (foiComprado == true).
            // o métod any percorre a lista de itens e verifica se algum deles tem a propriedade foiComprado marcada como true.
            //  Se encontrar pelo menos um item comprado, ele retorna true, caso contrário, retorna false.
            if (listaDeItens.any { it.foiComprado }) {
                ListaDeItens( // listaDeItens.filter { it.foiComprado } é usado para criar uma nova lista que contém apenas os itens que foram marcados como comprados (foiComprado == true).
                    lista = listaDeItens.filter { it.foiComprado },
                    aoMudarStatus = { itemSelecionado ->
                        /*
                    1. Entrada: Lista de itens (ex: Arroz, Feijão, Batata).
                    2. Ação do Map: Ele percorre a lista.
                    3. A Transformação:
                        "Você é o ‘item’ que eu cliquei?"
                        Se SIM: Eu te transformo numa versão "marcada como comprado".
                        Se NÃO: Eu te mantenho exatamente como você era.
                    4. Saída: Uma nova lista onde todos os itens continuam lá, mas aquele que você clicou está transformado.
                    * */
                        // 1. Criamos uma NOVA lista baseada na antiga usando o.map
                        listaDeItens = listaDeItens.map { itemMap ->

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
                    },
                    aoRemoverItem = { itemRemovido ->
                        listaDeItens = listaDeItens - itemRemovido

                    },
                    aoEditarItem = { itemEditado, novoTexto ->
                        listaDeItens = listaDeItens.map { itemAtual ->
                            if (itemAtual == itemEditado) {
                                itemAtual.copy(texto = novoTexto)
                            } else {
                                itemAtual
                            }
                        }
                    }
                )
            }
        }
    }
}


fun LazyListScope.ListaDeItens(
    // LazyListScope é o escopo usado para construir a lista usando o LazyColumn, permitindo que você defina os itens da lista de forma eficiente.
    lista: List<ItemCompra>,
    aoMudarStatus: (item: ItemCompra) -> Unit = {},
    aoRemoverItem: (item: ItemCompra) -> Unit = {},
    aoEditarItem: (item: ItemCompra, novoTexto: String) -> Unit = { _, _ -> },
) {
    items(lista.size) { index ->
        ItemDaLista(
            item = lista[index],
            aoMudarStatus = aoMudarStatus,
            aoRemoverItem = aoRemoverItem,
            aoEditarItem = aoEditarItem
        )

    }

}

@Composable
fun Titulo(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        style = Typography.headlineLarge,
        modifier = modifier
    )
}

@Composable
fun ItemDaLista(
    item: ItemCompra,
    aoMudarStatus: (item: ItemCompra) -> Unit = {},
    aoRemoverItem: (item: ItemCompra) -> Unit = {},
    aoEditarItem: (item: ItemCompra, novoTexto: String) -> Unit = { _, _ -> }, // _ recebe dois argumentos implicitos, o item a ser editado e o novo texto, mas como não estamos usando eles dentro da função lambda, podemos usar _ para indicar que esses argumentos não serão utilizados.
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.Top, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            var textoEditado by rememberSaveable() { mutableStateOf(item.texto) }
            var edicao by rememberSaveable() { mutableStateOf(false) }

            Checkbox(
                checked = item.foiComprado,
                onCheckedChange = { // Ação a ser executada quando o estado do checkbox for alterado
                    aoMudarStatus(item)
                },
                modifier = Modifier
                    .padding(end = 8.dp) // Adiciona um espaçamento entre o checkbox e o texto
                    .requiredSize(24.dp) // Define o tamanho do checkbox


            )

            if (edicao) {
                OutlinedTextField(
                    value = textoEditado,
                    onValueChange = { textoEditado = it },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        aoEditarItem(item, textoEditado)
                        edicao = false
                    },
                ) {
                    Icone(
                        icone = Icons.Default.Done,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            } else {
                Text(
                    text = item.texto,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f) // Faz com que o texto ocupe o máximo de espaço disponível
                )
            }

            IconButton(
                onClick = { aoRemoverItem(item) },
                modifier = Modifier.padding(horizontal = 8.dp) // Adiciona um espaçamento entre os ícones
            ) {
                Icone(
                    icone = Icons.Default.Delete,
                    modifier = Modifier
                        .size(16.dp) // Define o tamanho do ícone
                )
            }
            IconButton(
                onClick = {
                    edicao = true
                },
            ) {
                Icone(
                    icone = Icons.Default.Edit,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }

        Text(
            text = item.dataHora,
            style = Typography.labelSmall,
            modifier = Modifier
                .padding(top = 8.dp) // Adiciona um espaçamento entre o texto e o item da lista

        )
    }
}

@Composable
fun AdicionarItem(aoSalvarItem: (item: ItemCompra) -> Unit, modifier: Modifier = Modifier) {
    //mutableStateOf é uma função que cria um estado mutável, ou seja, um estado que pode ser alterado.
    // O valor inicial do estado é uma string vazia ("").
    // O texto digitado pelo usuário será armazenado nessa variável de estado,
    // permitindo que o valor seja atualizado conforme o usuário digita no TextField.

    //remember é uma função que permite que o estado seja lembrado durante recomposições.
    // Isso significa que, mesmo que a função composable seja chamada novamente
    // (por exemplo, devido a uma mudança de estado), o valor do estado será mantido e não será redefinido para o valor inicial.
    // Dessa forma, o texto digitado pelo usuário permanecerá mesmo que a função composable seja recomposicionada.

    //rememberSaveable é uma função que combina a funcionalidade de remember com a capacidade de
    // salvar o estado em caso de mudanças de configuração, como rotações de tela.

    var texto by rememberSaveable() { mutableStateOf("") }
    OutlinedTextField(
        value = texto,
        onValueChange = { texto = it },
        placeholder = {
            Text(
                text = "Digite o item que deseja adicionar",
                color = Color.Gray,
                style = Typography.bodyMedium
            )
        },

        singleLine = true, // Isso garante que o usuario digite apenas uma linha de texto, evitando que o campo de texto se expanda verticalmente.
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .fillMaxWidth() // Faz com que o componente ocupe todoo o espaço horizontal disponível que tem disponivel para ele
            .padding(8.dp),
    )

    Button(
        shape = RoundedCornerShape(24.dp),
        onClick = {
            aoSalvarItem(ItemCompra(texto, false, getDataHota()))
            texto = "" // Limpa o campo de texto após salvar o item
        },
        modifier = modifier
    ) {
        Text(
            text = "Salvar item",
            color = Color.White,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

fun getDataHota(): String {
    val dataHoraAtual = System.currentTimeMillis()
    val dataHoraFormata = SimpleDateFormat("EEEE (dd/MM/yyyy) 'às' HH:mm", Locale("pt", "BR"))
    return dataHoraFormata.format(dataHoraAtual)
}

@Composable
fun ImagemTopo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.topo),
        contentDescription = "Imagem Top",
        modifier = modifier.size(160.dp)
    )
}

@Composable
fun Icone(icone: ImageVector, modifier: Modifier = Modifier) {
    Icon(
        imageVector = icone,
        contentDescription = "Ícone editar",
        tint = Marinho,
        modifier = modifier
    )
}


@Preview
@Composable
private fun AdicionarItemPreview() {
    SuperComprasTheme() {
        AdicionarItem(aoSalvarItem = {})
    }
}

@Preview
@Composable
private fun ItemDaListaPreview() {
    SuperComprasTheme() {
        ItemDaListaPreview()
    }
}

@Preview
@Composable
private fun IconePreview() {
    Icone(icone = Icons.Default.Delete)
}

@Preview
@Composable
private fun ImagemTopPreview() {
    ImagemTopo()
}

@Preview
@Composable
private fun TituloPreview() {
    SuperComprasTheme() {
        Titulo("Lista de Compras")
    }
}

//Classe para representar os itens da lista de compras
data class ItemCompra(
    val texto: String,
    var foiComprado: Boolean = false,
    val dataHora: String
)