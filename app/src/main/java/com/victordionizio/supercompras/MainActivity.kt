package com.victordionizio.supercompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victordionizio.supercompras.ui.theme.Marinho
import com.victordionizio.supercompras.ui.theme.SuperComprasTheme
import com.victordionizio.supercompras.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperComprasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(verticalArrangement = Arrangement.Top) {
                        ImagemTopo(
                            modifier = Modifier.padding(innerPadding)
                        )
                        Titulo(
                            texto = "Lista de Compras",
                            modifier = Modifier.padding(innerPadding)
                        )
                        ItemDaLista(
                            modifier = Modifier.padding(innerPadding)
                        )
                        Titulo(
                            texto = "Comprados",
                            modifier = Modifier.padding(innerPadding)
                        )

                    }
                }
            }
        }
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
fun ItemDaLista(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Top, modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
            Checkbox(
                checked = false,
                onCheckedChange = { /*TODO*/ }, // Ação a ser executada quando o estado do checkbox for alterado
                modifier = Modifier
                    .padding(end = 8.dp) // Adiciona um espaçamento entre o checkbox e o texto
                    .requiredSize(24.dp) // Define o tamanho do checkbox


            )
            Text(
                text = "Suco",
                style = Typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier
            )
            Icone(
                icone = Icons.Default.Delete,
                modifier = Modifier
                    .padding(horizontal = 8.dp) // Adiciona um espaçamento entre o texto e o ícone
                    .size(16.dp) // Define o tamanho do ícone
            )
            Icone(
                icone = Icons.Default.Edit,
                modifier = Modifier
                    .size(16.dp)
            )
        }

        Text(
            text = "Segunda-geira, (25/05/2026) as 11:40",
        )
    }


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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuperComprasTheme {
        Greeting("Android")
    }
}