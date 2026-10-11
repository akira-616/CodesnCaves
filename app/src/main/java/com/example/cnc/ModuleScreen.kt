package com.example.cnc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(
    module: LearnModule,
    customTopics: List<Topic>,
    onAddCustom: (Topic) -> Unit,
    onRemoveCustom: (Int) -> Unit,
    onPractice: (trackIndex: Int, topicIndex: Int) -> Unit,
    onBack: () -> Unit,
    onContest: () -> Unit,
    onTopicOpened: () -> Unit
) {
    val isCustom = module.id == "custom"
    val languages = module.tracks.keys.toList()
    var selectedLang by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    val topics: List<Topic> = when {
        isCustom -> customTopics
        languages.isNotEmpty() -> module.tracks[languages[selectedLang]].orEmpty()
        else -> module.topics
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${module.emoji} ${module.title}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            if (isCustom) FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add topic")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            if (languages.isNotEmpty()) {
                TabRow(selectedTabIndex = selectedLang) {
                    languages.forEachIndexed { i, lang ->
                        Tab(selected = i == selectedLang, onClick = { selectedLang = i }, text = { Text(lang) })
                    }
                }
            }
            if (isCustom && topics.isEmpty()) {
                Box(Modifier.fillMaxSize().padding(24.dp)) {
                    Text("Tap + to add your own topics and build your learning path.")
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!isCustom) item { ContestBanner(onContest) }
                itemsIndexed(topics) { index, topic ->
                    TopicCard(
                        index + 1, topic, onTopicOpened,
                        onRemove = if (isCustom) ({ onRemoveCustom(index) }) else null,
                        onPractice = if (!isCustom && TopicQuizData.questionsFor(module.id, languages.getOrNull(selectedLang), topic.title).isNotEmpty())
                            ({ onPractice(if (languages.isNotEmpty()) selectedLang else -1, index) }) else null
                    )
                }
            }
        }
    }

    if (showDialog) AddTopicDialog(
        onDismiss = { showDialog = false },
        onAdd = { onAddCustom(it); showDialog = false }
    )
}

@Composable
private fun TopicCard(number: Int, topic: Topic, onOpen: () -> Unit, onRemove: (() -> Unit)? = null, onPractice: (() -> Unit)? = null) {
    var expanded by remember { mutableStateOf(false) }
    Card(onClick = { expanded = !expanded; if (expanded) onOpen() }, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("$number. ${topic.title}", fontWeight = FontWeight.SemiBold)
            if (expanded) {
                Spacer(Modifier.height(6.dp))
                Text(topic.description, style = MaterialTheme.typography.bodyMedium)
                if (onPractice != null) Button(onClick = onPractice) { Text("Practice questions") }
                if (onRemove != null) TextButton(onClick = onRemove) { Text("Remove") }
            }
        }
    }
}

@Composable
private fun AddTopicDialog(onDismiss: () -> Unit, onAdd: (Topic) -> Unit) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New topic") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Title") }, singleLine = true)
                OutlinedTextField(desc, { desc = it }, label = { Text("Notes (optional)") })
            }
        },
        confirmButton = {
            TextButton(onClick = { if (title.isNotBlank()) onAdd(Topic(title.trim(), desc.trim())) }) { Text("Add") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
private fun ContestBanner(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("🏁 Weekly Contest", fontWeight = FontWeight.Bold)
            Text("Answer fast and correctly to climb the leaderboard.", style = MaterialTheme.typography.bodySmall)
        }
    }
}
