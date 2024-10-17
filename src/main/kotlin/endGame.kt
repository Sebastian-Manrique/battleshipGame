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


@Composable
fun endGame() {
    Box(
        modifier = Modifier
            .defaultMinSize(minHeight = 900.dp)
            .fillMaxSize() // Take all the space available
            .background(Color.Black) // background black
            , contentAlignment = Alignment.Center
    ) {
        if (win.value){
            Text(text = "You win!", fontFamily = comic.toFontFamily(), color = Color.Cyan, fontSize = 100.sp)
        }
        else{
            Text(text = "You lose!", fontFamily = comic.toFontFamily(), color = Color.Cyan, fontSize = 100.sp)
        }
    }
}