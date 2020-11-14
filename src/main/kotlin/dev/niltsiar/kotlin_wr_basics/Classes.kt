@file:Suppress("unused")

package dev.niltsiar.kotlin_wr_basics

class Business(
    val name: String,
    members: List<Member> = emptyList()
) {

    private val _members: MutableList<Member> = members.toMutableList()
    val members: List<Member>
        get() = _members
}


abstract class Member(
    open val name: String
)

data class Subscriber(override val name: String) : Member(name)
data class DungeonMaster(override val name: String) : Member(name)
data class Minion(override val name: String, val position: String) : Member(name)
object FuckingOverlord : Member("SuperCoco")
