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

@Composable
@Preview
fun App() {

    val arr by remember { mutableStateOf(createArray()) }

    val comic = Font(
        resource = "fontC.ttf",
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    )

    MaterialTheme {
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 900.dp)
                .fillMaxSize() // Take all the space available
                .background(Color.Black) // background black
        ) {
            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    gridCall(arr)
                    Text("Your field", color = Color.White, fontFamily = comic.toFontFamily(), fontSize = 40.sp)
                }
                Spacer(modifier = Modifier.size(100.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    gridCallOpponent()
                    Text("Enemy field", color = Color.White, fontFamily = comic.toFontFamily(), fontSize = 40.sp)
                }
            }
        }
    }
}

@Composable
fun gridCall(arr: Array<IntArray>, modifier: Modifier = Modifier) {
    val listOfChars = listOf(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(11)
//        , modifier = Modifier.defaultMinSize(800.dp,800.dp)
    )
    {
        items(121) {
            var colorVar by remember { mutableStateOf(Color.LightGray) }
            val x = it % 11
            val y = (it - 1) / 11

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
                    ).clickable {
                        if (x == 0 || y == 0) {
                            return@clickable
                        }
//                        println("Touched x is $x and y is $y !!")
                        colorVar = when {
                            colorVar == Color.LightGray && arr[x - 1][y - 1] == 1 -> Color.Red
                            colorVar == Color.LightGray && arr[x - 1][y - 1] == 0 -> Color.Yellow
                            else -> Color.LightGray
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                if (x == 0) Text(text = "${y + 1}")
                else if (y == 0) Text(text = "${listOfChars[x]}")
            }
        }
    }
}

@Composable
fun gridCallOpponent(modifier: Modifier = Modifier) {
    val listOfChars = listOf(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(11)
//        , modifier = Modifier.defaultMinSize(800.dp,800.dp)
    )
    {
        for (x in 0..10) {
            for (y in 0..10) {
                items(1) {
                    var colorVar by remember { mutableStateOf(Color.Gray) } //When it's a hit
                    Box(
                        modifier = Modifier
                            .defaultMinSize(10.dp, 10.dp)
                            .padding(1.dp) // Reduce padding to make items smaller
                            .aspectRatio(1f) // Adjust aspect ratio to make the items smaller
                            .clip(RoundedCornerShape(5.dp))
                            .background(
                                when {
                                    x == 0 && y == 0 -> Color.Black // Paint the box in the position (0, 0) black
                                    else -> colorVar
                                }
                            )
                            .clickable {
                                if (x == 0 || y == 0) {
                                    return@clickable
                                }
                                colorVar = when (colorVar) {
                                    Color.Gray -> Color(0xFF04008E)
                                    else -> Color.Gray
                                }
                                println("Touched!!")
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (x == 0) Text(text = "${listOfChars[y]}")
                        else if (y == 0) Text(text = "$x")
                    }
                }
            }
        }
    }
}

fun createArray(): Array<IntArray> {
    val arr = Array(10) { IntArray(10) }
    for (y in arr.indices) {
        for (x in arr[y].indices) {
            arr[y][x] = (0..1).random()
        }
    }
    return arr
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(1200.dp, 650.dp))  // Specify  the inicial size of the window
    ) {
        App()
    }
}
