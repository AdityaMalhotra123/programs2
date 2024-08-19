
import java.util.Arrays
fun main() {
    println("Hello, from AIT")
    var a: Int = 10; a = 3
    val b = true || false
    //a = 5
    println("${a.inc()}")
    println("a = " + a)

    var myIntArray = intArrayOf(1, 2, 3)
    var myIntArray2 = Array<Int>(3, { it * 1 })
    println(Arrays.toString(myIntArray))
    println(Arrays.toString(myIntArray2))
    println(myIntArray[2])

    var mixedArray = arrayOf(1, "something")
    println(Arrays.toString(mixedArray))

    var rawString = """Hello!
        |bkjk
        |ln
        |""".trimMargin()
    println(rawString)

    var c: Int? = null
    c = 4
    c = null

    c?.toByte()

}

fun showMessage(message: String?) {
    val messageToBePrinted =
        message ?: throw java.lang.IllegalArgumentException("No message provided")
    println(messageToBePrinted)
}

fun whatShouldIDoToday(mood: String, weather: String, temperature: Int): String {
    return when {
        mood == "sad" && weather == "rainy" -> "Watch a good film"
        weather == "sunny" && temperature > 20 -> "Go and play in the garden!"
        temperature < 5 -> "Go to ski!"
        else -> "I don't know..."
    }
}