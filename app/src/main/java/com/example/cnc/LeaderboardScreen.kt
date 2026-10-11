package com.example.cnc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(title: String, moduleId: String?, vm: AppViewModel, onBack: () -> Unit) {
    val entries = Leaderboards.build(vm, moduleId)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("$title · ${ContestData.weekKey()}") },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } }
        )
    }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            if (Leaderboards.myRank(entries) == null) item { Text("Take part in this week's contest to get ranked.") }
            leaderboardRows(entries)
        }
    }
}

fun androidx.compose.foundation.lazy.LazyListScope.leaderboardRows(entries: List<Entry>, limit: Int = Int.MAX_VALUE) {
    itemsIndexed(entries.take(limit)) { i, e ->
        Card(
            colors = CardDefaults.cardColors(containerColor = if (e.isYou) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                val medal = when (i) { 0 -> "🥇"; 1 -> "🥈"; 2 -> "🥉"; else -> "#${i + 1}" }
                Text(medal, Modifier.width(44.dp), fontWeight = FontWeight.Bold)
                Text(if (e.isYou) "${e.name} (you)" else e.name, Modifier.weight(1f), fontWeight = if (e.isYou) FontWeight.Bold else FontWeight.Normal)
                Column(horizontalAlignment = Alignment.End) {
                    Text("${e.score} pts", fontWeight = FontWeight.SemiBold)
                    Text("${e.timeSec}s", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
