import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

var arrFriendly = mutableStateOf(createArray())
var arrOpponent = mutableStateOf(createArray())
var opponentShots = mutableStateOf(Array(10) { IntArray(10) })
val alreadyShot = mutableStateOf(Array(10) { IntArray(10) })
val listOfChars = listOf(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')

val comic = Font(
    resource = "fontC.ttf",
    weight = FontWeight.Bold,
    style = FontStyle.Normal
)

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 900.dp)
                .fillMaxSize() // Take all the space available
                .background(Color.Black) // background black
        ) {
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    gridCallOfFriendly(comic)
                    Text(
                        "Your field",
                        color = Color.White,
                        fontFamily = comic.toFontFamily(),
                        fontSize = 40.sp
                    )
                }
                Spacer(modifier = Modifier.size(100.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    gridCallOpponent(comic)
                    fun countArrayEnemy2() {
                        arrOpponent.value.forEachIndexed { rowIndex, row ->
                            row.forEachIndexed { colIndex, shot ->
                                if (shot == 1) {
                                    println("----------------BOATS IN :${listOfChars[rowIndex + 1]},${colIndex + 1}")
                                }
                            }
                        }
                    }
                    Text(
                        "Enemy field",
                        color = Color.White,
                        fontFamily = comic.toFontFamily(),
                        fontSize = 40.sp
                    )
                }
            }
        }
    }
}

fun main() = application {
    Window(
        title = "BattleShipGame",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(1200.dp, 650.dp))  // Specify the initial size of the window
    ) {
        App()
    }
}
