// Autor: Sebastian-Manrique
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp

@Composable
fun gridCallOfFriendly(comic: Font, modifier: Modifier = Modifier) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(11)
    ) {
        items(121) {  // Friendly grid
            val x = it % 11 // Calculate `x` based on the index
            val y = it / 11 // Calculate `y` based on index (subtract later in each case)

            val colorVar = when {
                x > 0 && y > 0 && opponentShots.value[x - 1][y - 1] == 1 -> {
                    if (arrFriendly.value[x - 1][y - 1] == 1) {
                        Color.Red // If there is a shot in the position (x,y) paint red (Ship)
                    } else {
                        Color.Cyan // If there is a shot in the position (x,y) paint cyan (Water)
                    }
                }

                x > 0 && y > 0 && arrFriendly.value[x - 1][y - 1] == 1 -> {
                    Color(0xFF006605) // If arr[x][y] == 1, it paints green
                }

                else -> {
                    Color.LightGray // Default colour is grey
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
        items(121) {     // Enemy grid
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
                                // println("Position shot : (${listOfChars[x]}, $y)") //Actual is X-1 and actual Y-1
                                opponentMove(x - 1, y - 1) // Modify state
                                countArrayFriendly()
                                winCheck()
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

@Composable
fun puttingTheShips(comic: Font, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(10)
    ) {
        items(20) {     // Enemy grid
            var colorVar by remember { mutableStateOf(Color.Gray) } // When it's a hit
            val x = it % 2
            val y = it / 2

            Box(
                modifier = Modifier
                    .defaultMinSize(10.dp, 10.dp)
                    .padding(1.dp) // Reduce padding to make items smaller
                    .aspectRatio(1f) // Adjust aspect ratio to make the items smaller
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        when {
                            x % 2 == 1 -> Color.Green // Paint the box in the position (0, 0) black
                            else -> colorVar
                        }
                    )
                    .then(Modifier
                        .clickable {
                            colorVar = when (x - 1) {
                                0 -> Color.Green
                                else -> Color.Gray
                            }
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                //pass
            }
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

    // Put the ones in the first 17 positions random
    for (i in 0 until 17) {
        val (y, x) = positions[i]
        arr[y][x] = 1
    }
    return arr
}

fun createShips(): Array<IntArray> {
    val arr = Array(2) { IntArray(10) }
    for (x in arr.indices) {
        for (y in arr[x].indices) {
            print("[ ${arr[x][y]} ]")
        }
        println()
    }
    return arr
}

fun opponentMove(actualX: Int, actualY: Int) {

    var validShot = false
    var rndX: Int
    var rndY: Int

    // Repeat until a valid position is found
    while (!validShot) {
        rndX = (0..9).random()
        rndY = (0..9).random()

        // Checks if it has already been triggered at the currentX, currentY co-ordinates
        if (alreadyShot.value[actualX][actualY] == 1) {
            println("Do nothing! This position was already shot.")
            break  // exits the loop if it has already been triggered in this position.
        } else if (opponentShots.value[rndX][rndY] == 0) {
            // If you have not previously fired in that position
            val newShots = opponentShots.value.map { it.copyOf() }.toTypedArray()
            newShots[rndX][rndY] = 1

            // Update the state with the new copy of the array.
            opponentShots.value = newShots
            validShot = true

            //println("Opponent shoots at position: (${listOfChars[rndX + 1]}, ${rndY + 1})")
            // Save the current position as fired in alreadyShot.
            alreadyShot.value[actualX][actualY] = 1
        }
    }
}

fun countArrayFriendly(): Boolean {
    var shotsCounter = 0
    var friendlyShipsHits = 0
    opponentShots.value.forEach { row2 ->
        row2.forEach { cell ->        // Iterate over the elements of each row
            if (cell == 1) {
                shotsCounter++
            }
        }
    }


    val friendlyShipsTotal = 17  // Total numbers of friendly ships

    // Recorre el array del oponente para contar los barcos hundidos
    arrFriendly.value.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, shot ->
            if (arrFriendly.value[rowIndex][colIndex] == 1 && opponentShots.value[rowIndex][colIndex] == 1) {
                // If there is a ship in that position and it has already been fired upon
                friendlyShipsHits++
            }
        }
    }
    println("Number of friendly boats hit: $friendlyShipsHits")
    println("Number of shots: $shotsCounter")    // Print the result
    // Verifica si se han destruido todos los barcos enemigos
    if (friendlyShipsHits == friendlyShipsTotal) {
        isWin.value = false  // El jugador ha ganado
        isLose.value = true
        println("You lose!")
        return true
    }
    return false
}

fun winCheck(): Boolean {
    var enemyBoatsHits = 0
    val enemyBoatsTotal = 17  // Número total de barcos enemigos

    // Recorre el array del oponente para contar los barcos hundidos
    arrOpponent.value.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, shot ->
            if (arrOpponent.value[rowIndex][colIndex] == 1 && alreadyShot.value[rowIndex][colIndex] == 1) {
                // Si hay un barco en esa posición y ya se ha disparado allí
                enemyBoatsHits++
            }
            if (arrOpponent.value[rowIndex][colIndex] == 1 && alreadyShot.value[rowIndex][colIndex] == 1) {
// pass
            } else if (arrOpponent.value[rowIndex][colIndex] == 1) {
                println(" Enemy's boats in : ${listOfChars[rowIndex + 1]} , ${colIndex + 1}")
            }
        }
    }

    // Verifica si se han destruido todos los barcos enemigos
    if (enemyBoatsHits == enemyBoatsTotal) {
        isWin.value = true  // El jugador ha ganado
        println("You win!")
        return true
    }

    return false
}

fun resetAllFun() {
    arrFriendly.value = createArray()
    arrOpponent.value = createArray()
    opponentShots.value = Array(10) { IntArray(10) }
    alreadyShot.value = Array(10) { IntArray(10) }
    isWin.value = false
    isLose.value = false
}