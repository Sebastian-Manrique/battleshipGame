import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

// Variables globales y fuentes
var arrFriendly = mutableStateOf(createArray())
var arrOpponent = mutableStateOf(createArray2())
var opponentShots = mutableStateOf(Array(10) { IntArray(10) })
val alreadyShot = mutableStateOf(Array(10) { IntArray(10) })
val listOfChars = listOf(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')

val comic = Font(
    resource = "fontC.ttf",
    weight = FontWeight.Bold,
    style = FontStyle.Normal
)

// Estados de las diferentes pantallas
enum class Screen {
    MainMenu,
    Game,
    EndGame
}

@Composable
fun App() {
    // Estado que guarda la pantalla actual
    var currentScreen by remember { mutableStateOf(Screen.MainMenu) }

    // Navegación basada en el estado de la pantalla
    when (currentScreen) {
        Screen.MainMenu -> MainMenuScreen(onStartGame = {
            currentScreen = Screen.Game
        })
        Screen.Game -> GameScreen(onEndGame = {
            currentScreen = Screen.EndGame
        })
        Screen.EndGame -> EndGameScreen(onRestart = {
            currentScreen = Screen.MainMenu
        })
    }
}

// Pantalla del menú principal
@Composable
fun MainMenuScreen(onStartGame: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "BattleShip Game",
                color = Color.White,
                fontFamily = comic.toFontFamily(),
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Start Game",
                color = Color.Green,
                fontSize = 30.sp,
                modifier = Modifier.clickable { onStartGame() } // Cambiar a la pantalla del juego
            )
        }
    }
}

// Pantalla del juego
@Composable
fun GameScreen(onEndGame: () -> Unit) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 900.dp)
                .fillMaxSize()
                .background(Color.Black)
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
                    Text(
                        "Enemy field",
                        color = Color.White,
                        fontFamily = comic.toFontFamily(),
                        fontSize = 40.sp
                    )
                }
            }
            // Aquí se puede agregar un botón para simular el fin del juego
            Text(
                text = "End Game",
                color = Color.Red,
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
                    .clickable { onEndGame() } // Cambiar a la pantalla de fin de juego
            )
        }
    }
}

// Pantalla de fin del juego
@Composable
fun EndGameScreen(onRestart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Game Over",
                color = Color.White,
                fontFamily = comic.toFontFamily(),
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Restart",
                color = Color.Green,
                fontSize = 30.sp,
                modifier = Modifier.clickable { onRestart() } // Cambiar a la pantalla de menú principal
            )
        }
    }
}

fun main() = application {
    Window(
        title = "BattleShipGame",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(1200.dp, 650.dp))
    ) {
        App()
    }
}