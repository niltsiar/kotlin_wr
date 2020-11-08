/*
This file is to be executed in an IntelliJ scratch file

// ____    ____      ___      .______       __       ___      .______    __       _______      _______.
//\   \  /   /     /   \     |   _  \     |  |     /   \     |   _  \  |  |     |   ____|    /       |
// \   \/   /     /  ^  \    |  |_)  |    |  |    /  ^  \    |  |_)  | |  |     |  |__      |   (----`
//  \      /     /  /_\  \   |      /     |  |   /  /_\  \   |   _  <  |  |     |   __|      \   \
//   \    /     /  _____  \  |  |\  \----.|  |  /  _____  \  |  |_)  | |  `----.|  |____ .----)   |
//    \__/     /__/     \__\ | _| `._____||__| /__/     \__\ |______/  |_______||_______||_______/
//

val anInt: Int = 1
val aLong: Long = 1_000_000_000_000
//val anInvalidFloat: Float = 1.0
val aFloat: Float = 1f
val aDouble: Double = 1.0

val anotherInt = 1
val anotherLong = 1L
val anotherFloat = 1f
val anotherDouble = 1.0


//____    ____      ___      .______         ____    ____    ____    ____      ___       __
//\   \  /   /     /   \     |   _  \        \   \  /   /    \   \  /   /     /   \     |  |
// \   \/   /     /  ^  \    |  |_)  |        \   \/   /      \   \/   /     /  ^  \    |  |
//  \      /     /  /_\  \   |      /          \_    _/        \      /     /  /_\  \   |  |
//   \    /     /  _____  \  |  |\  \----.       |  |           \    /     /  _____  \  |  `----.
//    \__/     /__/     \__\ | _| `._____|       |__|            \__/     /__/     \__\ |_______|
//

val thisIsAFinalVariable = "I'm a final variable"
var thisIsAMutableVariable = "I can mutate"
thisIsAMutableVariable = "This is my new value"

//thisIsAFinalVariable = "This will be an error"

val stringBuilder = StringBuilder()
stringBuilder.append("This is")
stringBuilder.append(" a mutable instance")
stringBuilder.append(" with some string templates $aDouble")

//stringBuilder = StringBuilder()
stringBuilder.clear()


//.__   __.  __    __   __       __           ___      .______    __       _______      _______.
//|  \ |  | |  |  |  | |  |     |  |         /   \     |   _  \  |  |     |   ____|    /       |
//|   \|  | |  |  |  | |  |     |  |        /  ^  \    |  |_)  | |  |     |  |__      |   (----`
//|  . `  | |  |  |  | |  |     |  |       /  /_\  \   |   _  <  |  |     |   __|      \   \
//|  |\   | |  `--'  | |  `----.|  `----. /  _____  \  |  |_)  | |  `----.|  |____ .----)   |
//|__| \__|  \______/  |_______||_______|/__/     \__\ |______/  |_______||_______||_______/
//

//val thisCannotBeNull: Int = null
var thisCanBeNull: String? = null
thisCanBeNull = "This can be null at some point"


// _______  __    __  .__   __.   ______  __    ______   .__   __.  _______      _______.
//|   ____||  |  |  | |  \ |  |  /      ||  |  /  __  \  |  \ |  | |   ____|    /       |
//|  |__   |  |  |  | |   \|  | |  ,----'|  | |  |  |  | |   \|  | |  |__      |   (----`
//|   __|  |  |  |  | |  . `  | |  |     |  | |  |  |  | |  . `  | |   __|      \   \
//|  |     |  `--'  | |  |\   | |  `----.|  | |  `--'  | |  |\   | |  |____ .----)   |
//|__|      \______/  |__| \__|  \______||__|  \______/  |__| \__| |_______||_______/
//

fun sum(a: Int, b: Int): Int {
    return a + b
}

val isEven = { i: Int -> i % 2 == 0 }

sum(1, 2)
isEven(1)

fun returnNothing(): Unit {
    print("This function does not return anything")
}

fun sumWithDefaults(a: Int = 0, b: Int = 1) = a + b
sumWithDefaults()
sumWithDefaults(5)
sumWithDefaults(b = 9)
sumWithDefaults(b = 10, a = 5)


// __    __   __    _______  __    __       ______   .______       _______   _______ .______
//|  |  |  | |  |  /  _____||  |  |  |     /  __  \  |   _  \     |       \ |   ____||   _  \
//|  |__|  | |  | |  |  __  |  |__|  |    |  |  |  | |  |_)  |    |  .--.  ||  |__   |  |_)  |
//|   __   | |  | |  | |_ | |   __   |    |  |  |  | |      /     |  |  |  ||   __|  |      /
//|  |  |  | |  | |  |__| | |  |  |  |    |  `--'  | |  |\  \----.|  '--'  ||  |____ |  |\  \----.
//|__|  |__| |__|  \______| |__|  |__|     \______/  | _| `._____||_______/ |_______|| _| `._____|
//
// _______  __    __  .__   __.   ______ .___________. __    ______   .__   __.      _______.
//|   ____||  |  |  | |  \ |  |  /      ||           ||  |  /  __  \  |  \ |  |     /       |
//|  |__   |  |  |  | |   \|  | |  ,----'`---|  |----`|  | |  |  |  | |   \|  |    |   (----`
//|   __|  |  |  |  | |  . `  | |  |         |  |     |  | |  |  |  | |  . `  |     \   \
//|  |     |  `--'  | |  |\   | |  `----.    |  |     |  | |  `--'  | |  |\   | .----)   |
//|__|      \______/  |__| \__|  \______|    |__|     |__|  \______/  |__| \__| |_______/
//

fun compute(i: Int, block: (Int) -> Boolean): Boolean = block(i)

compute(1, isEven)
compute(2, { number -> number % 2 != 0 })
compute(3) {
    it % 3 == 0
}


//     _______..___  ___.      ___      .______      .___________.     ______      ___           _______..___________.
//    /       ||   \/   |     /   \     |   _  \     |           |    /      |    /   \         /       ||           |
//   |   (----`|  \  /  |    /  ^  \    |  |_)  |    `---|  |----`   |  ,----'   /  ^  \       |   (----``---|  |----`
//    \   \    |  |\/|  |   /  /_\  \   |      /         |  |        |  |       /  /_\  \       \   \        |  |
//.----)   |   |  |  |  |  /  _____  \  |  |\  \----.    |  |        |  `----. /  _____  \  .----)   |       |  |
//|_______/    |__|  |__| /__/     \__\ | _| `._____|    |__|         \______|/__/     \__\ |_______/        |__|
//
fun computeOverAny(i: Any, block: (Int) -> Boolean): Boolean? {
    //block(i)

    return if (i is Int) {
        block(i)
    } else {
        null
    }
}


//.___  ___.  __       _______.  ______  _______  __           ___      .__   __.  _______      ___
//|   \/   | |  |     /       | /      ||   ____||  |         /   \     |  \ |  | |   ____|    /   \
//|  \  /  | |  |    |   (----`|  ,----'|  |__   |  |        /  ^  \    |   \|  | |  |__      /  ^  \
//|  |\/|  | |  |     \   \    |  |     |   __|  |  |       /  /_\  \   |  . `  | |   __|    /  /_\  \
//|  |  |  | |  | .----)   |   |  `----.|  |____ |  `----. /  _____  \  |  |\   | |  |____  /  _____  \
//|__|  |__| |__| |_______/     \______||_______||_______|/__/     \__\ |__| \__| |_______|/__/     \__\
//
val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
for (item in fruits) {
    println(item)
}
for (index in fruits.indices) {
    println("fruit at $index is ${fruits[index]}")
}

val fruitsRange = 0..fruits.size
println(fruitsRange)
println(0 until fruits.size)
println(fruits.size downTo 0 step 2)

for (index in fruitsRange) {
    try {
        println("fruit at $index is ${fruits[index]}")
    } catch (e: Throwable) {
        println(e)
    }
}

fruits//.asSequence()
    .map { it.toUpperCase() }
    .onEachIndexed { index, _ -> println(index) }
    .filter { it.startsWith("A") }
    //.take(1)
    .sortedBy { it }
    .forEach { println(it) }

*/
