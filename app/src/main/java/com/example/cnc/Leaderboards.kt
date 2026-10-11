package com.example.cnc

import kotlin.random.Random

data class Entry(val name: String, val score: Int, val timeSec: Int, val isYou: Boolean)

/**
 * Local leaderboard: simulated players + you. Replace `build` with a backend call
 * (e.g. Firestore query ordered by score desc, time asc) for real multi-user ranking.
 * Rank = highest score first; ties broken by lowest total time.
 */
object Leaderboards {
    private val bots = listOf(
        "Aarav", "Mia", "Liam", "Sofia", "Noah", "Zara", "Ethan", "Priya", "Lucas", "Hana",
        "Omar", "Chloe", "Ravi", "Emma", "Kenji", "Nina", "Arjun", "Isla", "Leo", "Tara"
    )

    private fun botResult(bot: String, moduleId: String, week: String): Pair<Int, Int> {
        val qs = ContestData.questionsFor(moduleId)
        val r = Random((bot + moduleId + week).hashCode())
        var score = 0; var time = 0
        repeat(qs.size) {
            val t = r.nextInt(5, 55)
            time += t
            score += ContestData.scoreFor(r.nextFloat() < 0.6f, t)
        }
        return score to time
    }

    /** moduleId = null -> global (all modules this week) */
    fun build(vm: AppViewModel, moduleId: String?): List<Entry> {
        val week = ContestData.weekKey()
        val ids = if (moduleId == null) ContestData.contestModuleIds else listOf(moduleId)
        val list = bots.map { b ->
            val results = ids.map { botResult(b, it, week) }
            Entry(b, results.sumOf { it.first }, results.sumOf { it.second }, false)
        }.toMutableList()
        val mine = vm.attempts.filter { it.week == week && it.moduleId in ids }
        if (mine.isNotEmpty()) list.add(Entry(vm.profile.label, mine.sumOf { it.score }, mine.sumOf { it.timeSec }, true))
        return list.sortedWith(compareByDescending<Entry> { it.score }.thenBy { it.timeSec })
    }

    fun myRank(entries: List<Entry>): Int? = entries.indexOfFirst { it.isYou }.takeIf { it >= 0 }?.plus(1)
}
