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

    wr.addMember(dani)
    wr.addMember(juanjo)
    wr.addMember(niltsiar)

    println(wr.members)

}

fun DungeonMaster.sayIntro() {
    println(this.intro)
}

val DungeonMaster.intro: String
    get() = "Mi nombre es $name, soy programdor y formador y bla bla bla"
