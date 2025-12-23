package day21

import common.day

// answer #1: 121
// answer #2: 201

fun main() {
    day(n = 21) {
        part1 { input ->
            val boss = input.lines.map { it.split(": ").last().toInt() }
                .let { Boss(it[0], it[1], it[2]) }

            players
                .filter { willPlayerWin(it, boss) }
                .minBy { it.cost }
                .cost
        }
        verify {
            expect result 121
        }

        part2 { input ->
            val boss = input.lines.map { it.split(": ").last().toInt() }
                .let { Boss(it[0], it[1], it[2]) }

            players
                .filter { !willPlayerWin(it, boss) }
                .maxBy { it.cost }
                .cost
        }
        verify {
            expect result 201
        }
    }
}

private fun willPlayerWin(
    player: Player,
    boss: Boss,
): Boolean {
    var playerHealth = player.hp
    var bossHealth = boss.hp
    while(true) {
        bossHealth -= (player.totalDamage - boss.armor).coerceAtLeast(1)
        if (bossHealth <= 0) return true

        playerHealth -= (boss.damage - player.totalArmor).coerceAtLeast(1)
        if (playerHealth <= 0) return false
    }
}

private fun <T> combinations(items: List<T>): List<List<T>> = buildList {
    // 0 items
    add(emptyList())
    // 1 item
    addAll(items.map { listOf(it) })
    // 2 items
    for (i in items.indices) {
        for (j in i + 1 until items.size) {
            add(listOf(items[i], items[j]))
        }
    }
}

private data class Boss(
    val hp: Int,
    val damage: Int,
    val armor: Int,
)

private data class Weapon(val value: Int, val cost: Int)
private val weapons = listOf(
    Weapon(value = 4, cost = 8),
    Weapon(value = 5, cost = 10),
    Weapon(value = 6, cost = 25),
    Weapon(value = 7, cost = 40),
    Weapon(value = 8, cost = 74),
)

private data class Armor(val value: Int, val cost: Int)
private val armor = listOf(
    Armor(value = 1, cost = 13),
    Armor(value = 2, cost = 31),
    Armor(value = 3, cost = 53),
    Armor(value = 4, cost = 75),
    Armor(value = 5, cost = 102),
)

private interface Ring {
    val value: Int
    val cost: Int
}

private data class DamageRing(override val value: Int, override val cost: Int) : Ring
private val damageRings = listOf(
    DamageRing(value = 1, cost = 25),
    DamageRing(value = 2, cost = 50),
    DamageRing(value = 3, cost = 100),
)

private data class ArmorRing(override val value: Int, override val cost: Int) : Ring
private val armorRings = listOf(
    ArmorRing(value = 1, cost = 20),
    ArmorRing(value = 2, cost = 40),
    ArmorRing(value = 3, cost = 80),
)

private data class Player(
    val hp: Int,
    val weapon: Weapon?,
    val armor: Armor?,
    val rings: List<Ring>,
) {
    val totalDamage: Int = (weapon?.value ?: 0) +
        rings.filterIsInstance<DamageRing>().sumOf { it.value }

    val totalArmor: Int = (armor?.value ?: 0) +
        rings.filterIsInstance<ArmorRing>().sumOf { it.value }

    val cost = (weapon?.cost ?: 0) + (armor?.cost ?: 0) + rings.sumOf { it.cost }
}

private val weaponChoices = (weapons.map { listOf(it) } + emptyList())
private val armorChoices = (armor.map { listOf(it) } + emptyList())
private val ringChoices = combinations(damageRings + armorRings)

private val players = buildList {
    weaponChoices.forEach { weapon ->
        val weapon = weapon.singleOrNull()
        armorChoices.forEach { armor ->
            val armor = armor.singleOrNull()
            ringChoices.forEach { rings ->
                this += Player(
                    hp = 100,
                    weapon = weapon,
                    armor = armor,
                    rings = rings,
                )
            }
        }
    }
}

