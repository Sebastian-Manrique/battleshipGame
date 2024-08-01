import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

fun main() {
    val textArea = JTextArea()
    textArea.setText("Here it goes the text")
    val scrollPane = JScrollPane(textArea)
    val list = arrayListOf<Int>() //Array
    for (i in 1..10) {
        list.addAll(listOf(i))
    }
    for (i in list.indices) { //count the array
        print(list[i])
    }
    val frame = JFrame("Hello, Kotlin/Swing")
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setSize(Dimension(600, 400))
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
}