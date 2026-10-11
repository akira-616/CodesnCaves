# Codes N Caves

An Android app (Kotlin) that helps new programmers find where to start. It shows ten learning
modules, runs **weekly timed contests** in every module, ranks players on a **leaderboard**, and
keeps a **profile with a GitHub-style activity grid**.

- Package / applicationId: `com.example.cnc`
- Language: Kotlin, Jetpack Compose (new screens) + XML Views (original quiz screens)
- minSdk 24, compileSdk 36
- Based on the `Phoenix1808/Jams-26` quiz app (GDG JSS). See [What changed](#what-changed-from-the-original-repo).

> Not yet compiled in CI. Open in Android Studio, sync Gradle, and fix anything it flags.
> See `docs/SETUP_GUIDE.md` for setup + Git, and `docs/DOCUMENTATION.md` for the full technical docs.

## Features

| Area | What it does |
|------|--------------|
| Home | 2-column grid of 10 modules: Basic, DSA, System Design, Architecture, Cloud Computing, Computer Network, Operating System, DBMS, Create Your Own, Competitive |
| Basic | 8 topics: General, Sequential Logic/Loops, OOPs, Selection/Conditions, Functions, Variables & Constants, Data Structures, Jump Statements |
| DSA | Python / Java / C++ tabs, each with its own topic list (built-ins or collections/STL, user-defined structures, linked list, trees, graphs, tries, practice) |
| 6 knowledge modules | System Design, Architecture, Cloud, Networking, OS, DBMS: 10 topics each |
| Competitive | 10 contest-skills topics + the global weekly ranking |
| Create Your Own | Add/remove your own topics; saved on the device |
| Topic practice | Every topic (104 across all modules) has 3 practice questions with instant right/wrong feedback: untimed and unranked. Expand a topic and tap **Practice questions** |
| Weekly contests | Every module except Create Your Own: 10 questions, 60 s each, one attempt per week |
| Leaderboards | Per-module and global (this week). Tab "Compete" |
| Profile | Name, display name/title, email, rank, total points, active days, streak, 20-week activity grid |
| Theme | "Blue eclipse": `#0F0E47`, `#272757`, `#505081`, `#8686AC` |

### Scoring and ranking
- Correct answer = 100 points + up to 60 speed bonus (`60 - secondsTaken`). Wrong or timed-out = 0.
- Rank = highest score first; ties go to the lowest total time.
- Each module has a bank of 12 questions; 10 are picked each ISO week (same for everyone, different every week).
- Practice questions (3 per topic, 288 in total) are separate from contests and never affect rank; finishing a practice set counts as activity.

## Project structure

```
codesncaves/
├── settings.gradle.kts              rootProject.name = "codesncaves"
├── README.md
├── docs/
│   ├── DOCUMENTATION.md             architecture, file-by-file reference, how to extend
│   └── SETUP_GUIDE.md              open in Android Studio, add the files, Git/GitHub steps
└── app/
    ├── build.gradle.kts             namespace/applicationId com.example.cnc, new dependencies
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── res/values/strings.xml          app_name = "Codes N Caves"
        │   └── java/com/example/cnc/
        │       ├── SplashActivity.kt           [original, 1 line changed] splash -> HomeActivity
        │       ├── MainActivity.kt             [original] 4-question mini quiz (XML)
        │       ├── ResultActivity.kt           [original] mini quiz result (XML)
        │       ├── HomeActivity.kt             [NEW] hosts the Compose app
        │       ├── AppNavigation.kt            [NEW] NavHost + bottom bar
        │       ├── AppViewModel.kt             [NEW] profile, activity, attempts, custom topics
        │       ├── Models.kt                   [NEW] Topic, LearnModule
        │       ├── CurriculumData.kt           [NEW] all module/topic content
        │       ├── HomeScreen.kt               [NEW] module grid
        │       ├── ModuleScreen.kt             [NEW] topic list, DSA tabs, custom topics
        │       ├── ContestData.kt              [NEW] weekly question bank, scoring, week key
        │       ├── TopicQuizData.kt            [NEW] 3 practice questions for every topic
        │       ├── PracticeScreen.kt           [NEW] untimed practice with instant feedback
        │       ├── ContestScreen.kt            [NEW] timed quiz
        │       ├── Leaderboards.kt             [NEW] ranking logic (local)
        │       ├── LeaderboardScreen.kt        [NEW] leaderboard UI
        │       ├── CompeteScreen.kt            [NEW] Compete tab
        │       ├── ProfileScreen.kt            [NEW] profile + activity grid
        │       └── ui/theme/
        │           ├── Color.kt                [MODIFIED] Blue eclipse palette
        │           ├── Theme.kt                [MODIFIED] CncTheme (was QuizAppTheme)
        │           └── Type.kt                 [original]
        ├── test/.../ExampleUnitTest.kt         [original, package renamed]
        └── androidTest/.../ExampleInstrumentedTest.kt   [original, package renamed]
```

## What changed from the original repo

| File | Change |
|------|--------|
| Package | `com.example.quizapp` -> `com.example.cnc` in every Kotlin file, `namespace`, `applicationId`, and the instrumented test |
| `settings.gradle.kts` | `rootProject.name` "QuizApp" -> "codesncaves" |
| `app/build.gradle.kts` | new namespace/applicationId; added `navigation-compose`, `lifecycle-viewmodel-compose`, core library desugaring |
| `AndroidManifest.xml` | registered `HomeActivity`; **removed six entries that had no class** (see below) |
| `res/values/strings.xml` | app name -> "Codes N Caves" |
| `SplashActivity.kt` | opens `HomeActivity` instead of the quiz |
| `ui/theme/Color.kt`, `Theme.kt` | replaced Material purple defaults with the Blue eclipse palette; `QuizAppTheme` -> `CncTheme`; dynamic colour removed |
| 15 new Kotlin files | everything marked **NEW** above |

**Untouched:** `MainActivity.kt`, `ResultActivity.kt` (package line only), `Type.kt`, all layouts
(`activity_main/result/splash.xml`), all drawables, icons, `colors.xml`, `themes.xml`, Gradle
wrapper, version catalog, `.gitignore`.

The mini quiz (`MainActivity` -> `ResultActivity`) still exists and works, but nothing launches it
now that the splash goes to the home screen.

## Missing from the original repo

- Six manifest entries pointing at classes that were never committed: `SplashActivity1`,
  `MainActivity2`, `ResultActivity2`, `MainActivity3`, `SplashActivity3`, `ResultActivity3`
  (and their layouts). They were removed from the manifest because launching one would crash.
- No `README.md` (this file) and no `LICENSE`.
- `local.properties` is not in Git (correct: it holds your local SDK path and is generated by Android Studio).
- No automated tests beyond the two Android Studio templates.

## Known limitations

- **Leaderboards are simulated.** Other players are generated locally; only your own results are real.
  Real multi-user ranking needs a backend (Firebase/Firestore or your own API). All ranking logic is in
  `Leaderboards.kt`.
- Module topics are outlines (title, short description and 3 practice questions), not full lessons.
- Data lives in SharedPreferences: clearing app data or switching phones resets it. There is no login.

## Credits and licence

Original quiz app: `Phoenix1808/Jams-26`, "© 2026 | Made by GDG JSS" (shown on the splash screen).
The original repository has no licence file, so keep the credit and make sure you have the owner's
permission before publishing this under your own account. Add a `LICENSE` once decided.
