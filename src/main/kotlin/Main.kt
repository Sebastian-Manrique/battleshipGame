import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

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
            gridCall()
        }
    }
}

@Composable
fun gridCall() {
    val listOfChars = listOf(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')
    LazyVerticalGrid(
        columns = GridCells.Fixed(11),
        content = {
            for (x in 0..10) {
                for (y in 0..10) {
                    items(1) {
                        Box(
                            modifier = Modifier
                                .padding(0.5.dp) // Reduce padding to make items smaller
                                .aspectRatio(0.8f) // Adjust aspect ratio to make the items smaller
                                .clip(RoundedCornerShape(5.dp))
                                .background(
                                    when {
                                        x == 0 && y == 0 -> Color.Black // Paint the box in the position (0, 0) black
                                        else -> Color.Gray
                                    }
                                )
                                .then(
                                    if (x == 0 || y == 0) {
                                        Modifier
                                    } else {
                                        Modifier.clickable {
                                            println("Touched!!")
                                        }
                                    }
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (x == 0) Text(text = "${listOfChars[y]}")
                            else if (y == 0) Text(text = "$x")
                        }
                    }
                }
            }
        }
    )

}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(900.dp, 925.dp))  // Specify  the inicial size of the window
    ) {
        App()
    }
}
