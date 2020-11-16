@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.niltsiar.kotlin_wr_basics

class Business(
    val name: String,
    members: List<Member> = emptyList()
) {

    private val _members: MutableList<Member> = members.toMutableList()
    val members: List<Member>
        get() = _members

    operator fun plusAssign(member: Member) {
        _members.add(member)
    }

    operator fun minusAssign(member: Member) {
        _members.remove(member)
    }

    operator fun get(index: Int): Member = _members[index]

    operator fun get(name: String): Member? = _members.find { it.name == name }

    operator fun component1(): String = name
    operator fun component2(): List<Member> = members
}

operator fun Business.contains(member: Member): Boolean = members.contains(member)


abstract class Member(
    open val name: String
) {

    infix fun subscribeTo(business: Business) {
        business += this
    }

    infix fun cancelSubscriptionFrom(business: Business) {
        business -= this
    }
}

data class Subscriber(override val name: String) : Member(name)
data class DungeonMaster(override val name: String) : Member(name)
data class Minion(override val name: String, val position: String) : Member(name)
object FuckingOverlord : Member("SuperCoco") {
    override fun toString(): String = name
}
