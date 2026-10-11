package com.example.cnc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompeteScreen(vm: AppViewModel, onContest: (String) -> Unit) {
    val global = Leaderboards.build(vm, null)
    val rank = Leaderboards.myRank(global)
    Scaffold(topBar = { TopAppBar(title = { Text("Weekly Contests") }) }) { pad ->
        LazyColumn(Modifier.padding(pad), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Your global rank this week", style = MaterialTheme.typography.bodySmall)
                        Text(
                            if (rank != null) "#$rank of ${global.size}" else "Not ranked yet",
                            style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            item { Text("This week's contests", style = MaterialTheme.typography.titleMedium) }
            items(CurriculumData.modules.filter { it.id in ContestData.contestModuleIds }) { m ->
                Card(onClick = { onContest(m.id) }, modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(16.dp)) {
                        Text("${m.emoji} ${m.title}", Modifier.weight(1f))
                        Text(if (vm.hasAttempted(m.id)) "Done ✓" else "Open", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
            item { Text("Global leaderboard (top 10)", style = MaterialTheme.typography.titleMedium) }
            leaderboardRows(global, limit = 10)
        }
    }
}
