package com.example.cnc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val RightGreen = Color(0xFF2E7D32)
private val WrongRed = Color(0xFFB3261E)

/** Untimed, unranked practice for one topic: instant feedback after each answer. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(module: LearnModule, track: String?, topic: Topic, vm: AppViewModel, onBack: () -> Unit) {
    val qs = remember { TopicQuizData.questionsFor(module.id, track, topic.title) }
    var idx by remember { mutableIntStateOf(0) }
    var picked by remember { mutableIntStateOf(-1) }
    var correct by remember { mutableIntStateOf(0) }
    var done by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(topic.title, maxLines = 1) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } }
        )
    }) { pad ->
        Column(
            Modifier.padding(pad).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when {
                qs.isEmpty() -> Text("No practice questions for this topic yet.")
                done -> {
                    Text("Practice complete", style = MaterialTheme.typography.headlineMedium)
                    Text("You got $correct of ${qs.size} right.")
                    Text(
                        when {
                            correct == qs.size -> "Perfect. Try the weekly contest next."
                            correct >= qs.size / 2 -> "Good work. One more round will lock it in."
                            else -> "Worth another pass: re-read the topic and retry."
                        }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { idx = 0; picked = -1; correct = 0; done = false }) { Text("Retry") }
                        OutlinedButton(onClick = onBack) { Text("Back to topics") }
                    }
                }
                else -> {
                    val q = qs[idx]
                    val revealed = picked >= 0
                    Text("Question ${idx + 1} of ${qs.size}", style = MaterialTheme.typography.bodySmall)
                    LinearProgressIndicator(progress = { (idx + 1) / qs.size.toFloat() }, modifier = Modifier.fillMaxWidth())
                    Text(q.text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    q.options.forEachIndexed { i, opt ->
                        val colors = when {
                            revealed && i == q.answer -> ButtonDefaults.buttonColors(containerColor = RightGreen, contentColor = Color.White)
                            revealed && i == picked -> ButtonDefaults.buttonColors(containerColor = WrongRed, contentColor = Color.White)
                            else -> ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = Color.White)
                        }
                        Button(
                            onClick = { if (!revealed) { picked = i; if (i == q.answer) correct++ } },
                            colors = colors,
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(opt) }
                    }
                    if (revealed) {
                        Text(if (picked == q.answer) "Correct!" else "Not quite. The right answer is highlighted in green.")
                        Button(onClick = {
                            if (idx + 1 < qs.size) { idx++; picked = -1 } else { done = true; vm.logActivity() }
                        }) { Text(if (idx + 1 < qs.size) "Next" else "Finish") }
                    }
                }
            }
        }
    }
}
