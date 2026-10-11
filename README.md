# Codes & Caves

An Android app that helps new programmers find where to start. Pick a topic, learn the basics, practice with quizzes, and compete in weekly contests.

Built with Kotlin and Jetpack Compose (Material 3). It also keeps the original quick-quiz screens from the [Jams-26](https://github.com/Phoenix1808/Jams-26) project.

## Features

- **10 learning modules:** Basic, DSA, System Design, Architecture, Cloud Computing, Computer Network, Operating System, DBMS, Create Your Own, and Competitive.
- **Basic** has 8 topics: General, Sequential Logic / Loops, OOPs, Selection / Conditions, Functions, Variables & Constants, Data Structures, Jump Statements.
- **DSA** has separate tracks for Python, Java and C++ (built-in structures, libraries, user-defined structures, linked lists, trees, graphs, tries and practice).
- **System Design, Architecture, Cloud, Networks, OS and DBMS** each have 10 beginner-friendly topics.
- **Practice quizzes:** every module has one (DSA has one per language). Untimed, with instant right/wrong feedback.
- **Weekly contests:** every module except Create Your Own. Timed and ranked, one attempt per module per week.
- **Leaderboards:** per-module weekly ranking and a global weekly ranking.
- **Profile:** name, display name and email, plus your rank, total points, active days, streak and a GitHub-style activity grid.
- **Create Your Own:** add and delete your own topics, saved on the device.
- **Mini Quiz:** the original 4-question quiz, opened from the Compete tab.

## How scoring works

- 100 points for each correct answer, plus up to 60 bonus points for answering quickly (60 minus the seconds taken).
- Each question has a 60-second limit. Running out of time counts as wrong.
- Each week a contest uses 5 questions drawn from the module's question set. Everyone gets the same 5, and they change every week.
- Ranking is by highest score, with ties broken by lower total time.

## Tech stack

| | |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 (plus XML views for the original quiz) |
| Navigation | Navigation Compose |
| State | `AndroidViewModel` with Compose state |
| Storage | SharedPreferences (on-device) |
| Min SDK | 24 (`java.time` via core library desugaring) |
| Package | `com.example.cnc` |

## Colour palette: Blue eclipse

| Colour | Hex | Used for |
|---|---|---|
| Midnight | `#0F0E47` | Background |
| Deep navy | `#272757` | Cards and surfaces |
| Indigo | `#505081` | Secondary / containers |
| Lavender | `#8686AC` | Primary / accents |

## Getting started

1. Open the project in Android Studio.
2. Make sure `app/build.gradle.kts` includes:
   ```kotlin
   // inside android { compileOptions { ... } }
   isCoreLibraryDesugaringEnabled = true

   // inside dependencies { ... }
   coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")
   implementation("androidx.navigation:navigation-compose:2.8.3")
   implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
   implementation("androidx.appcompat:appcompat:1.7.0")
   implementation("androidx.constraintlayout:constraintlayout:2.1.4")
   implementation("com.google.android.material:material:1.12.0")
   ```
3. Click **Sync Now**.
4. Create an emulator in Device Manager (or plug in a phone with USB debugging on).
5. Press **Run**.

## Project structure

All Kotlin files are in `app/src/main/java/com/example/cnc/`.

| File | What it does |
|---|---|
| `SplashActivity.kt` | Launcher screen; opens `HomeActivity` |
| `HomeActivity.kt` | Entry point for the Compose app |
| `AppNavigation.kt` | Bottom bar (Home, Compete, Profile) and routes |
| `HomeScreen.kt` | Grid of the 10 modules |
| `ModuleScreen.kt` | Topics, DSA language tabs, contest and practice cards |
| `CurriculumData.kt`, `Models.kt` | Module and topic content |
| `QuestionBank.kt` | The 120 questions (12 sets of 10) |
| `PracticeScreen.kt` | Untimed practice quiz |
| `ContestData.kt`, `ContestScreen.kt` | Scoring, weekly question draw, timed contest |
| `Leaderboards.kt`, `LeaderboardScreen.kt`, `CompeteScreen.kt` | Rankings and the Compete tab |
| `ProfileScreen.kt` | Profile, stats and activity grid |
| `AppViewModel.kt` | Saves profile, activity, results and custom topics |
| `MainActivity.kt`, `ResultActivity.kt` | Original XML quiz and result screens |
| `ui/theme/` | Colours, typography and `CncTheme` |

## Adding questions

Open `QuestionBank.kt` and add a line to the right list:

```kotlin
q("Question text?", 2, "Option A", "Option B", "Correct option", "Option D")
```

The number is the index of the correct option, starting at 0.

## Known limitations

- **The leaderboard is local.** Other players are simulated. Real rankings between users need a backend (for example Firebase) and a way for users to sign in.
- Topics have short descriptions, not full lessons.
- Questions are per module, not per topic, and are all multiple choice. There is no code editor or judge.
- Create Your Own has no questions or contest.
- The original quiz screens keep their old orange styling.
- Unit tests have not been written.

## Credits

Original quiz app: Jams-26 (GDG JSS).
