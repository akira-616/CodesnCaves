# Codes N Caves: Technical Documentation

## 1. Architecture

Two UI technologies live side by side:

- **Original screens (XML Views):** `SplashActivity`, `MainActivity` (mini quiz), `ResultActivity`.
- **New screens (Jetpack Compose):** everything inside `HomeActivity`.

```
SplashActivity ──(1.5 s)──► HomeActivity ──► AppNavigation (NavHost)
                                                 │
        ┌──────────────┬─────────────────────────┼───────────────────────┐
     "home"         "compete"                 "profile"        module/{id} · contest/{id} · leaderboard/{id}
   HomeScreen     CompeteScreen             ProfileScreen      ModuleScreen · ContestScreen · LeaderboardScreen
                                                 │
                                           AppViewModel  ◄── single state holder (SharedPreferences)
                                                 │
                    CurriculumData (static content)   ContestData (questions, scoring)   Leaderboards (ranking)
```

Layers:
1. **Content (static):** `Models.kt`, `CurriculumData.kt`, `ContestData.kt`. No Android dependencies.
2. **State:** `AppViewModel` holds profile, activity, attempts and custom topics as Compose state and persists them.
3. **Logic:** `Leaderboards.kt` builds rankings from state + simulated players.
4. **UI:** the `*Screen.kt` files. They read state and call ViewModel functions; they never touch storage directly.

## 2. File-by-file reference

| File | Responsibility |
|------|----------------|
| `SplashActivity.kt` | Original splash; after 1.5 s starts `HomeActivity`. |
| `HomeActivity.kt` | `ComponentActivity` that calls `setContent { CncTheme { Surface { AppNavigation() } } }`. |
| `AppNavigation.kt` | `NavHost` + bottom `NavigationBar` (Home, Compete, Profile). Routes: `home`, `compete`, `profile`, `module/{id}`, `practice/{id}/{trackIndex}/{topicIndex}` (trackIndex is -1 for modules without language tabs), `contest/{id}`, `leaderboard/{id}`. Bottom bar only shows on the three tab routes. |
| `Models.kt` | `Topic(title, description)` and `LearnModule(id, emoji, title, subtitle, topics, tracks)`. `tracks` maps language -> topics and is used only by DSA. |
| `CurriculumData.kt` | The 10 modules and all topics. `byId(id)` looks one up. **Edit this to change learning content.** |
| `HomeScreen.kt` | `LazyVerticalGrid` (2 columns) of module cards. |
| `ModuleScreen.kt` | Topic list. Adds language `TabRow` when `tracks` is non-empty (DSA), a "Weekly Contest" banner for every module except `custom`, and add/remove for the custom module. Expanding a topic logs activity. |
| `ContestData.kt` | `Question`, `Profile`, `Attempt` models; scoring (`scoreFor`); `weekKey()`; the question bank; `questionsFor(moduleId)`. |
| `TopicQuizData.kt` | Practice questions: 3 per topic, 96 sets (288 questions). Key = `moduleId/topic title`; DSA uses `dsa/<Language>/<title>` with `dsa/*/<title>` for topics shared by all languages (Linked List, Trees, Graphs, Tries). Titles must match `CurriculumData` exactly. |
| `PracticeScreen.kt` | Untimed practice for one topic: pick an answer, see green/red feedback, Next/Finish, score, Retry. Finishing logs activity but does not change rank. |
| `ContestScreen.kt` | Timed quiz. States: no questions / already attempted this week / in progress / finished. |
| `Leaderboards.kt` | `build(vm, moduleId?)` returns sorted `Entry` list; `myRank()` returns your 1-based position or null. |
| `LeaderboardScreen.kt` | Leaderboard list UI + `leaderboardRows()` helper reused by the Compete tab. |
| `CompeteScreen.kt` | Your global rank, this week's contest list (Done/Open), global top 10. |
| `ProfileScreen.kt` | Editable profile, stats (rank, points, active days, streak), `ActivityHeatmap`. |
| `AppViewModel.kt` | State + persistence (see section 5). |
| `ui/theme/*` | Palette, `CncTheme` (dark Material3 scheme), typography. |

## 3. Contest flow

1. User taps a module's **Weekly Contest** banner (or a row on the Compete tab).
2. `ContestScreen` asks `ContestData.questionsFor(moduleId)` for 10 questions.
   The 12-question bank is shuffled with a seed of `weekKey + moduleId`, so everyone gets the same
   10 in the same order that week, and a different set next week.
3. A timer counts up per question. Choosing an option calls `answer(i)`; reaching 60 s calls `answer(-1)`
   (counts as wrong).
4. Points per question: `100 + max(0, 60 - seconds)` if correct, else 0.
5. After the last question, `vm.submitAttempt(moduleId, score, totalSeconds)` stores the result **once**
   (a second call for the same module and week is ignored) and logs activity for today.
6. The result screen links to that module's leaderboard.

### Ranking (`Leaderboards.build`)
- Simulated players (20 fixed names) get deterministic pseudo-random results seeded by `name + module + week`
  (about 60% accuracy, 5 to 55 s per question), so the board is stable within a week.
- Your entry is added if you have at least one attempt that week in scope. Global scope sums all modules.
- Sort: score descending, then total time ascending.

## 4. Activity grid and streak
- `activity` maps `yyyy-MM-dd` -> action count. Actions: opening a topic, adding a custom topic, finishing a contest.
- The grid is 20 weeks x 7 days (Sunday on top), shades: 0 = `#272757`, 1 = `#505081`, 2-3 = `#8686AC`, 4+ = light lavender.
- Streak counts consecutive active days ending today (or yesterday, if you have not been active yet today).

## 5. Persistence
SharedPreferences file `cnc_prefs`:

| Key | Type | Content |
|-----|------|---------|
| `name`, `display`, `email` | String | Profile fields |
| `activity` | StringSet | `"yyyy-MM-dd:count"` |
| `attempts` | StringSet | `"moduleId|week|score|timeSec"` |
| `custom_topics` | String | JSON array of `{t, d}` |

Clearing app data resets everything. Email is stored locally only and is never sent anywhere.

## 6. How to extend

**Add or change questions:** edit the `BANK` map in `ContestData.kt`. Use
`q("text", indexOfCorrect, "opt0", "opt1", "opt2", "opt3")`. Keep at least `QUESTIONS_PER_CONTEST` (10) per module.

**Add a topic:** add a `t("Title", "Description")` line in the right list in `CurriculumData.kt`, then add a matching key with 3 questions in `TopicQuizData.kt` (a topic without questions simply shows no Practice button).

**Add a module:** add a `LearnModule(...)` to `CurriculumData.modules`; to give it a contest, add a same-id entry to `BANK`.

**Change scoring or timing:** `SECONDS_PER_QUESTION`, `QUESTIONS_PER_CONTEST` and `scoreFor` in `ContestData.kt`.

**Make the leaderboard real:** keep `Entry`, replace `Leaderboards.build` with a backend call
(e.g. a Firestore query `where week == X and module == Y orderBy score desc, timeSec asc`) and write
attempts in `AppViewModel.submitAttempt`. Add authentication so a user cannot submit scores for others,
and validate scores on the server (client-side scoring can be tampered with).

**Reach the old mini quiz again:** start it from anywhere with
`startActivity(Intent(context, MainActivity::class.java))`.

## 7. Dependencies (added)
- `androidx.navigation:navigation-compose:2.8.3`
- `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7`
- `com.android.tools:desugar_jdk_libs:2.1.4` (+ `isCoreLibraryDesugaringEnabled = true`) so `java.time` works on minSdk 24.
Compose BOM `2024.09.00` already supplies Material3 1.3, which the theme needs (`surfaceContainer*` colours).

## 8. Troubleshooting
- **"Unresolved reference R"**: you have leftover files in the old `com.example.quizapp` folder or the namespace was not updated. Check `app/build.gradle.kts` and delete the old folder.
- **Duplicate class errors**: the old `quizapp` package files still exist next to the new `cnc` ones; delete the old folder.
- **Red `surfaceContainer` colours**: material3 is older than 1.2; sync with the BOM.
- **App crashes on launch with "Unable to instantiate activity"**: a manifest entry points at a missing class; compare with the manifest in this project.
- **Contest says "already taken"**: by design, one attempt per module per week. Clear app data to reset while testing.
- **Build cannot download artifacts**: check your network or proxy settings in Android Studio.
