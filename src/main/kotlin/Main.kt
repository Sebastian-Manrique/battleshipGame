import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier
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
                                .padding(1.dp)
                                .aspectRatio(1f)
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
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
