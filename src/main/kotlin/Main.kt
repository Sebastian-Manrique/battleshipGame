import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
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

var arr = mutableStateOf(createArray())
var arrOpponent = mutableStateOf(createArray())
var opponentShots = mutableStateOf(Array(10) { IntArray(10) })
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

@Composable
fun gridCallOfFriendly(comic: Font, modifier: Modifier = Modifier) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(11)
    ) {
        items(121) { it ->    // Friendly grid
            val x = it % 11 // Calcula `x` basado en el índice
            val y = it / 11 // Calcula `y` basado en el índice (no restes 1 aquí)

            val colorVar = when {
                x > 0 && y > 0 && opponentShots.value[x - 1][y - 1] == 1 -> {
                    if (arr.value[x - 1][y - 1] == 1) {
                        Color.Red // If there is a shot in the position (x,y) paint red (Ship)
                    } else {
                        Color.Cyan // If there is a shot in the position (x,y) paint cyan (Water)
                    }
                }

                x > 0 && y > 0 && arr.value[x - 1][y - 1] == 1 -> {
                    Color(0xFF006605) // Si arr[x][y] == 1, pinta verde
                }

                else -> {
                    Color.LightGray // Usa el color por defecto
                }
            }

            Box(
                modifier = Modifier
                    .defaultMinSize(10.dp, 10.dp)
                    .padding(1.dp) // Reduce padding to make items smaller
                    .aspectRatio(1f) // Adjust aspect ratio to make the items smaller
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        when {
                            it == 0 -> Color.Black // Paint the box in the position (0, 0) black
                            else -> colorVar
                        }
                    ),
                contentAlignment = Alignment.Center,
            ) {
                if (x == 0) {
                    Text(text = "$y", fontFamily = comic.toFontFamily())
                } else if (y == 0) {
                    Text(text = "${listOfChars[x]}", fontFamily = comic.toFontFamily())
                }
            }
        }
    }
}

@Composable
fun gridCallOpponent(comic: Font, modifier: Modifier = Modifier) {


    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(11)
    ) {
        items(121) { it ->    // Enemy grid
            var colorVar by remember { mutableStateOf(Color.Gray) } // When it's a hit
            val x = it % 11
            val y = it / 11

            Box(
                modifier = Modifier
                    .defaultMinSize(10.dp, 10.dp)
                    .padding(1.dp) // Reduce padding to make items smaller
                    .aspectRatio(1f) // Adjust aspect ratio to make the items smaller
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        when {
                            it == 0 -> Color.Black // Paint the box in the position (0, 0) black
                            else -> colorVar
                        }
                    )
                    .then(
                        if (x == 0 || y == 0) {
                            Modifier
                        } else Modifier
                            .clickable {
                                colorVar = when (arrOpponent.value[x - 1][y - 1]) {
                                    1 -> Color.Red
                                    0 -> Color.Cyan
                                    else -> Color.Gray
                                }
                                println("Position shot : (${listOfChars[x]}, $y)")
                                //Actual X and actual Y
                                opponentMove(x, y) // Modify state
                            }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (x == 0) Text(text = "$y", fontFamily = comic.toFontFamily())
                else if (y == 0) Text(text = "${listOfChars[x]}", fontFamily = comic.toFontFamily())
            }
        }
    }
}

fun opponentMove(actualX: Int, actualY: Int) {
    val alreadyShot = mutableStateOf(Array(10) { IntArray(10) })
    println("Actual shot(start of opponentMove): $actualX, $actualY," +
            "\nposition in the alreadyShot array= ${alreadyShot.value[actualX][actualY]}")
    var validShot = false
    var rndX: Int
    var rndY: Int

    // Repite hasta que encuentres una posición válida
    while (!validShot) {
        rndX = (0..9).random()
        rndY = (0..9).random()
        if (alreadyShot.value[actualX][actualY] == 1) {
            println("Do nothing! This position was already shot.")
            break  // Sale del bucle si ya se disparó en esta posición
        } else
        // Si no ha disparado previamente en esa posición
            if (opponentShots.value[rndX][rndY] == 0) {
                val newShots = opponentShots.value.map { it.copyOf() }.toTypedArray() // <----- XDDDDDDDDDDDDDDDDDDDDDDD
                /*
                * El problema era que compose no detectaba si había modificado el array, por lo que no se actualizaba
                */
                newShots[rndX][rndY] = 1

                // Actualiza el estado con la nueva copia del array
                opponentShots.value = newShots
                validShot = true

                println("Opponent shoots at position: (${listOfChars[rndX + 1]}, ${rndY + 1})")
                println("Actual shot(inside of the while):  (${listOfChars[actualX]}, $actualY)")
                alreadyShot.value[actualX][actualY] = 1
            }
    }
}

fun createArray(): Array<IntArray> {
    val arr = Array(10) { IntArray(10) }

    // Create a list with all the positions posibles in the array (0  99)
    val positions = mutableListOf<Pair<Int, Int>>()
    for (y in arr.indices) {
        for (x in arr[y].indices) {
            positions.add(Pair(y, x))
        }
    }

    // Shuffle the list to obtain the random positions
    positions.shuffle()

    // Put the ones in the first 20 positions random
    for (i in 0 until 20) {
        val (y, x) = positions[i]
        arr[y][x] = 1
    }

    return arr
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
