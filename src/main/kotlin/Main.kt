import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


@Composable
fun App() {
    MaterialTheme {
        buttonCall()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun buttonCall(){
    Row{
        Button(onClick = {
        }) {
            Text("Acabar turno")
        }
    }
Spacer(modifier = Modifier.fillMaxSize().drawBehind { drawCircle(Color.Red) })
}