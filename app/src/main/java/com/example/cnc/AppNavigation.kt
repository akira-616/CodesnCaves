package com.example.cnc

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

@Composable
fun AppNavigation(vm: AppViewModel = viewModel()) {
    val nav = rememberNavController()
    val route = nav.currentBackStackEntryAsState().value?.destination?.route
    val tabs = listOf(Triple("home", "Home", "🏠"), Triple("compete", "Compete", "🏆"), Triple("profile", "Profile", "👤"))
    val idArg = listOf(navArgument("id") { type = NavType.StringType })

    Scaffold(bottomBar = {
        if (route in tabs.map { it.first }) NavigationBar {
            tabs.forEach { (r, label, emoji) ->
                NavigationBarItem(
                    selected = route == r,
                    onClick = { nav.navigate(r) { popUpTo("home"); launchSingleTop = true } },
                    icon = { Text(emoji) }, label = { Text(label) }
                )
            }
        }
    }) { pad ->
        NavHost(nav, startDestination = "home", modifier = Modifier.padding(pad)) {
            composable("home") { HomeScreen { nav.navigate("module/${it.id}") } }
            composable("compete") { CompeteScreen(vm) { nav.navigate("contest/$it") } }
            composable("profile") { ProfileScreen(vm) }
            composable("module/{id}", idArg) { e ->
                CurriculumData.byId(e.arguments?.getString("id"))?.let { m ->
                    ModuleScreen(
                        m, vm.customTopics, vm::addCustomTopic, vm::removeCustomTopic,
                        onBack = { nav.popBackStack() },
                        onContest = { nav.navigate("contest/${m.id}") },
                        onPractice = { t, i -> nav.navigate("practice/${m.id}/$t/$i") },
                        onTopicOpened = { vm.logActivity() }
                    )
                }
            }
            composable(
                "practice/{id}/{track}/{index}",
                listOf(
                    navArgument("id") { type = NavType.StringType },
                    navArgument("track") { type = NavType.IntType },
                    navArgument("index") { type = NavType.IntType }
                )
            ) { e ->
                val m = CurriculumData.byId(e.arguments?.getString("id"))
                val t = e.arguments?.getInt("track") ?: -1
                val i = e.arguments?.getInt("index") ?: 0
                if (m != null) {
                    val lang = if (t >= 0) m.tracks.keys.toList().getOrNull(t) else null
                    val list = if (lang != null) m.tracks[lang].orEmpty() else m.topics
                    list.getOrNull(i)?.let { topic ->
                        PracticeScreen(m, lang, topic, vm, onBack = { nav.popBackStack() })
                    }
                }
            }
            composable("contest/{id}", idArg) { e ->
                CurriculumData.byId(e.arguments?.getString("id"))?.let { m ->
                    ContestScreen(m, vm, onBack = { nav.popBackStack() }, onLeaderboard = { nav.navigate("leaderboard/${m.id}") })
                }
            }
            composable("leaderboard/{id}", idArg) { e ->
                CurriculumData.byId(e.arguments?.getString("id"))?.let { m ->
                    LeaderboardScreen(m.title, m.id, vm, onBack = { nav.popBackStack() })
                }
            }
        }
    }
}
