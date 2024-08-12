import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun App() {
    MaterialTheme {
        butonLlamado()
    }

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
@Composable
fun butonLlamado(){
    var text by remember { mutableStateOf("Hello, World!") }
    Row{
        Button(onClick = {
        }) {
            Text(text)
        }
        Button(onClick = {
        }) {
            Text(text+"sadasdasd")
        }
    }

}