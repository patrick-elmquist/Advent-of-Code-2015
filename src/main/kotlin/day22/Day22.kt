package day22

import common.day

// answer #1: 900
// answer #2: 1216

private enum class Spell(
    val cost: Int,
    val damage: Int = 0,
    val heal: Int = 0,
) {
    MagicMissile(53, 4),
    Drain(73, 2, 2),
    Shield(113),
    Poison(173),
    Recharge(229);

    val effect: Pair<Spell, Int>?
        get() = when (this) {
            MagicMissile,
            Drain,
                -> null

            Shield -> this to 6
            Poison -> this to 6
            Recharge -> this to 5
        }
}

fun main() {
    day(n = 22) {
        part1 { input ->
            val (bossHp, bossDmg) = input.lines.map { it.split(": ").last().toInt() }

            min = Int.MAX_VALUE
            Spell.entries
                .mapNotNull { startSpell ->
                    rec(
                        player = 50 to 500,
                        boss = bossHp to bossDmg,
                        effects = emptyMap(),
                        spell = startSpell,
                        consumedMana = 0,
                        hardMode = false,
                    )
                }
                .min()
        }
        verify {
            expect result 900
        }

        part2 { input ->
            val (bossHp, bossDmg) = input.lines.map { it.split(": ").last().toInt() }

            min = Int.MAX_VALUE
            Spell.entries
                .mapNotNull { startSpell ->
                    rec(
                        player = 50 to 500,
                        boss = bossHp to bossDmg,
                        effects = emptyMap(),
                        spell = startSpell,
                        consumedMana = 0,
                        hardMode = true,
                    )
                }
                .min()
        }
        verify {
            expect result 1216
        }
    }
}

var min = Int.MAX_VALUE
private fun rec(
    player: Pair<Int, Int>,
    boss: Pair<Int, Int>,
    effects: Map<Spell, Int>,
    spell: Spell,
    consumedMana: Int,
    hardMode: Boolean,
): Int? {
    var (bossHp, bossDmg) = boss
    var (hp, mana) = player
    val effects = effects.toMutableMap()
    val consumedMana = consumedMana + spell.cost

    if (consumedMana > min) {
        return null
    }

    if (hardMode) {
        hp--
        if (hp <= 0) {
            return null
        }
    }

    fun applyEffects() {
        bossHp -= effects.getPoisonDmg()
        mana += effects.getMana()
    }

    fun reduceEffects() {
        effects.replaceAll { _, duration -> duration - 1 }
        effects.entries.retainAll { it.value > 0 }
    }

    // Player cast spell
    applyEffects()
    if (bossHp <= 0) {
        min = minOf(min, consumedMana)
        return consumedMana
    }
    reduceEffects()

    hp += spell.heal
    mana -= spell.cost
    spell.effect?.let { effects[it.first] = it.second }
    bossHp -= spell.damage

    // Boss turn
    applyEffects()
    if (bossHp <= 0) {
        min = minOf(min, consumedMana)
        return consumedMana
    }
    reduceEffects()
    val actualBossDmg = (bossDmg - effects.getShield()).coerceAtLeast(1)

    hp -= actualBossDmg
    if (hp <= 0) {
        return null
    }

    val availableSpells = Spell.entries
        .filter { effects.getOrDefault(it, 0) <= 1 }
        .filter { it.cost <= mana }

    if (availableSpells.isEmpty()) {
        return null
    }

    return availableSpells
        .mapNotNull {
            rec(
                player = hp to mana,
                boss = bossHp to bossDmg,
                effects = effects.toMap(),
                spell = it,
                consumedMana = consumedMana,
                hardMode = hardMode,
            )
        }
        .minOfOrNull { it }
}

private fun Map<Spell, Int>.getShield(): Int =
    if (this.containsKey(Spell.Shield)) 7 else 0

private fun Map<Spell, Int>.getPoisonDmg(): Int =
    if (this.containsKey(Spell.Poison)) 3 else 0

private fun Map<Spell, Int>.getMana(): Int =
    if (this.containsKey(Spell.Recharge)) 101 else 0
