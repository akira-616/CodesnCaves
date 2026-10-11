package com.example.cnc

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cnc.ui.theme.DeepNavy
import com.example.cnc.ui.theme.Indigo
import com.example.cnc.ui.theme.Lavender
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(vm: AppViewModel) {
    var name by remember { mutableStateOf(vm.profile.name) }
    var display by remember { mutableStateOf(vm.profile.displayName) }
    var email by remember { mutableStateOf(vm.profile.email) }
    val global = Leaderboards.build(vm, null)
    val rank = Leaderboards.myRank(global)

    Scaffold(topBar = { TopAppBar(title = { Text("Profile") }) }) { pad ->
        Column(
            Modifier.padding(pad).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(display, { display = it }, label = { Text("Display name / title (shown on leaderboards)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(email, { email = it }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Button(onClick = { vm.saveProfile(Profile(name.trim(), display.trim(), email.trim())) }) { Text("Save") }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Stat("Rank", if (rank != null) "#$rank" else "—", Modifier.weight(1f))
                Stat("Total pts", "${vm.totalScore}", Modifier.weight(1f))
                Stat("Active days", "${vm.activeDays}", Modifier.weight(1f))
                Stat("Streak", "${vm.streak}🔥", Modifier.weight(1f))
            }

            Text("Activity", style = MaterialTheme.typography.titleMedium)
            ActivityHeatmap(vm.activity)
            Text("Opening topics and finishing contests count as activity.", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun Stat(label: String, value: String, modifier: Modifier) {
    Card(modifier) {
        Column(Modifier.padding(10.dp)) {
            Text(value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

/** GitHub-style grid: 20 weeks x 7 days, Sunday at the top. */
@Composable
fun ActivityHeatmap(activity: Map<String, Int>, weeks: Int = 20) {
    val today = LocalDate.now()
    val start = today.minusDays((today.dayOfWeek.value % 7).toLong() + 7L * (weeks - 1))
    val shades = listOf(DeepNavy, Indigo, Lavender, Color(0xFFC9C9E8))
    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        for (w in 0 until weeks) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                for (d in 0..6) {
                    val date = start.plusDays(w * 7L + d)
                    val color = if (date.isAfter(today)) Color.Transparent else {
                        when (activity[date.toString()] ?: 0) { 0 -> shades[0]; 1 -> shades[1]; 2, 3 -> shades[2]; else -> shades[3] }
                    }
                    Box(Modifier.size(12.dp).background(color, RoundedCornerShape(2.dp)))
                }
            }
        }
    }
}
