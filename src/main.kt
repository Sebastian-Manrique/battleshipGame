import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextField

fun main() {

    val frame = JFrame("Hello, Kotlin/Swing")
    var t = JTextField()
    val list = arrayListOf<Int>() //Array
    for (i in 1..10) {
        list.addAll(listOf(i))
    }
    for (i in list.indices) { //count the array
        print("["+list[i]+"]")
        t = JTextField("" + "["+list[i]+"]")
        val scrollPane = JScrollPane(t)
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER)
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setSize(Dimension(600, 400))
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
}