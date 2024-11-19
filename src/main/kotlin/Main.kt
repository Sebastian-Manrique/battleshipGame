// Autor: Sebastian-Manrique ⚓
import androidx.compose.animation.Crossfade
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
import kotlin.system.exitProcess

// Global variables and the Comic Sans, encapsulated in a state
var arrFriendly = mutableStateOf(createArray())
var arrOpponent = mutableStateOf(createShips())
var opponentShots = mutableStateOf(Array(10) { IntArray(10) })
var alreadyShot = mutableStateOf(Array(10) { IntArray(10) })
val listOfChars = listOf(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')
val isWin = mutableStateOf(false)
val isLose = mutableStateOf(false)
val comic = Font(
    resource = "fontC.ttf",
    weight = FontWeight.Bold,
    style = FontStyle.Normal
)

enum class Screen {
    MainMenu,
    Game,
    EndGame
}

@Composable
fun App() {
    // Current screen state
    var currentScreen by remember { mutableStateOf(Screen.MainMenu) }

    // Navigation based on screen state
    Crossfade(targetState = currentScreen) { screen ->
        when (screen) {
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
}

@Composable
fun MainMenuScreen(onStartGame: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "BattleShip game",
                color = Color.White,
                fontFamily = comic.toFontFamily(),
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Start game",
                color = Color.Green,
                fontSize = 30.sp,
                modifier = Modifier.clickable {
                    onStartGame()
                    resetAllFun()
                }
            )
        }
        Text(
            text = "Exit game",
            color = Color.Red,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .clickable {
                    exitProcess(0)
                }
        )
    }
}

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
                    puttingTheShips(comic)
                    Text(
                        "Yours ships",
                        color = Color.White,
                        fontFamily = comic.toFontFamily(),
                        fontSize = 40.sp
                    )
                }
            }

            // Using LaunchedEffect to detect changes in game state
            LaunchedEffect(isWin.value) {
                if (isWin.value) {
                    onEndGame()  // Call the function that handles the end of the game
                }
            }
            LaunchedEffect(isLose.value) {
                if (isLose.value) {
                    onEndGame()  // Same as the top
                }
            }
        }
    }
}

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
                if (isWin.value) "You win!" else "You lose!",
                color = Color.White,
                fontFamily = comic.toFontFamily(),
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Restart",
                color = Color.Green,
                fontSize = 30.sp,
                modifier = Modifier.clickable {
                    onRestart()
                }
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