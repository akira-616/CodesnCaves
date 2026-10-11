package com.example.cnc

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContestScreen(module: LearnModule, vm: AppViewModel, onBack: () -> Unit, onLeaderboard: () -> Unit) {
    val qs = remember { ContestData.questionsFor(module.id) }
    val already = remember { vm.hasAttempted(module.id) }
    var idx by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var total by remember { mutableIntStateOf(0) }
    var secs by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }

    fun answer(choice: Int) {
        if (finished) return
        val ok = choice == qs[idx].answer
        score += ContestData.scoreFor(ok, secs)
        total += secs
        if (idx + 1 < qs.size) idx++ else {
            finished = true
            vm.submitAttempt(module.id, score, total)
        }
    }

    // Per-question timer; auto-submits a wrong answer at the limit
    LaunchedEffect(idx, finished) {
        if (!already && !finished && qs.isNotEmpty()) {
            secs = 0
            while (secs < ContestData.SECONDS_PER_QUESTION) { delay(1000); secs++ }
            answer(-1)
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("${module.title} · Weekly Contest") },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } }
        )
    }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            when {
                qs.isEmpty() -> Text("No contest for this module yet.")
                already && !finished -> {
                    Text("You've already taken this week's contest. Come back next week!")
                    Button(onClick = onLeaderboard) { Text("View leaderboard") }
                }
                finished -> {
                    Text("Finished!", style = MaterialTheme.typography.headlineMedium)
                    Text("Score: $score pts · Time: ${total}s")
                    Button(onClick = onLeaderboard) { Text("View leaderboard") }
                }
                else -> {
                    val q = qs[idx]
                    Text("Question ${idx + 1} of ${qs.size}  ·  ${secs}s")
                    LinearProgressIndicator(progress = { secs / ContestData.SECONDS_PER_QUESTION.toFloat() }, modifier = Modifier.fillMaxWidth())
                    Text(q.text, style = MaterialTheme.typography.titleMedium)
                    q.options.forEachIndexed { i, opt ->
                        OutlinedButton(onClick = { answer(i) }, modifier = Modifier.fillMaxWidth()) { Text(opt) }
                    }
                    Text("Faster correct answers earn more points.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
