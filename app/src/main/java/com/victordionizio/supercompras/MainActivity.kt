package com.victordionizio.supercompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victordionizio.supercompras.ui.theme.SuperComprasTheme
import com.victordionizio.supercompras.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperComprasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Titulo(
                        modifier = Modifier.padding(innerPadding)
                    )

//                    ImagemTopo(
//                        modifier = Modifier.padding(innerPadding)
//                    )

//                    Icone( icone = Icons.Default.Delete,
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
            }
        }
    }
}

@Composable
fun Titulo(modifier: Modifier = Modifier) {
    Text(
        text = "Lista de Compras",
        modifier = modifier,
        style = Typography.headlineLarge
    )
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
fun Icone(icone : ImageVector, modifier: Modifier = Modifier) {
    Icon(
        imageVector = icone,
        contentDescription = "Ícone editar",
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
        Titulo()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuperComprasTheme {
        Greeting("Android")
    }
}