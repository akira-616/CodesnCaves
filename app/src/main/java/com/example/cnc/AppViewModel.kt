package com.example.cnc

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

/**
 * Single source of truth for user data. Everything is stored on-device in SharedPreferences.
 * To share leaderboards between devices, replace the persistence + Leaderboards.build() with a backend.
 */
class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = app.getSharedPreferences("cnc_prefs", Context.MODE_PRIVATE)

    var profile by mutableStateOf(
        Profile(
            prefs.getString("name", "") ?: "",
            prefs.getString("display", "") ?: "",
            prefs.getString("email", "") ?: ""
        )
    )
        private set

    /** "yyyy-MM-dd" -> number of actions that day (drives the activity grid) */
    val activity = mutableStateMapOf<String, Int>().apply {
        (prefs.getStringSet("activity", emptySet()) ?: emptySet()).forEach { s ->
            val p = s.split(":")
            if (p.size == 2) put(p[0], p[1].toIntOrNull() ?: 0)
        }
    }

    val attempts = mutableStateListOf<Attempt>().apply {
        (prefs.getStringSet("attempts", emptySet()) ?: emptySet()).forEach { s ->
            val p = s.split("|")
            if (p.size == 4) add(Attempt(p[0], p[1], p[2].toIntOrNull() ?: 0, p[3].toIntOrNull() ?: 0))
        }
    }

    /** Topics the user added in the "Create Your Own" module. */
    val customTopics = mutableStateListOf<Topic>().apply {
        try {
            val arr = JSONArray(prefs.getString("custom_topics", "[]"))
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                add(Topic(o.getString("t"), o.optString("d")))
            }
        } catch (e: Exception) { /* corrupted data: start empty */ }
    }

    fun saveProfile(p: Profile) {
        profile = p
        prefs.edit().putString("name", p.name).putString("display", p.displayName).putString("email", p.email).apply()
    }

    fun logActivity() {
        val k = LocalDate.now().toString()
        activity[k] = (activity[k] ?: 0) + 1
        prefs.edit().putStringSet("activity", activity.map { "${it.key}:${it.value}" }.toSet()).apply()
    }

    fun hasAttempted(moduleId: String) =
        attempts.any { it.moduleId == moduleId && it.week == ContestData.weekKey() }

    fun submitAttempt(moduleId: String, score: Int, timeSec: Int) {
        if (hasAttempted(moduleId)) return
        attempts.add(Attempt(moduleId, ContestData.weekKey(), score, timeSec))
        prefs.edit().putStringSet("attempts", attempts.map { "${it.moduleId}|${it.week}|${it.score}|${it.timeSec}" }.toSet()).apply()
        logActivity()
    }

    fun addCustomTopic(t: Topic) { customTopics.add(t); saveCustom(); logActivity() }
    fun removeCustomTopic(index: Int) { if (index in customTopics.indices) { customTopics.removeAt(index); saveCustom() } }

    private fun saveCustom() {
        val arr = JSONArray()
        customTopics.forEach { arr.put(JSONObject().put("t", it.title).put("d", it.description)) }
        prefs.edit().putString("custom_topics", arr.toString()).apply()
    }

    val activeDays get() = activity.size
    val totalScore get() = attempts.sumOf { it.score }

    val streak: Int
        get() {
            var d = LocalDate.now()
            if (!activity.containsKey(d.toString())) d = d.minusDays(1)
            var n = 0
            while (activity.containsKey(d.toString())) { n++; d = d.minusDays(1) }
            return n
        }
}
