package dev.niltsiar.kotlin_wr_basics

fun main() {
    val wr = Business("Web Reactiva Premium")

    val dani = DungeonMaster("Dani")
    val juanjo = Minion("Juan José", "Trello master")
    val niltsiar = Minion("Borja", "Voz en off")

    println(wr)
    println(dani)
    println(juanjo)
    println(FuckingOverlord)

    dani.sayIntro()

    wr += dani
    wr += juanjo
    wr += niltsiar
    wr += FuckingOverlord

    println(wr.members)

    println(wr[1])
    println(wr["Borja"])
    println(juanjo in wr)

    wr -= FuckingOverlord

    val (wrName, wrMembers) = wr
    println(wrName)
    println(wrMembers)
}

fun DungeonMaster.sayIntro() {
    println(this.intro)
}

val DungeonMaster.intro: String
    get() = "Mi nombre es $name, soy programdor y formador y bla bla bla"
