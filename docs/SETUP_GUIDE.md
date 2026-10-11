# Setup Guide: Android Studio, adding the files, and Git

## Part A. Opening the original repo in Android Studio

1. Install Android Studio (current stable) and Git. In Android Studio: **Settings > Version Control > Git**, make sure the Git path is detected.
2. **File > New > Project from Version Control** (on the Welcome screen: **Get from VCS**).
3. Choose **Repository URL**, version control **Git**, URL `https://github.com/Phoenix1808/Jams-26.git`.
4. Set **Directory** to the folder you want, for example `.../codesncaves` (this is the *folder* name; it does not change the app name). Click **Clone**.
5. Trust the project. Android Studio runs a **Gradle sync**. Wait for the bottom bar to finish. If it asks to install an SDK platform (API 36) or update a plugin, accept.
6. Run the original once (green Run button, emulator or phone) to confirm the base project builds: you should see "Mini Quizzz !!" and a 4-question quiz.

What you have now is the base layer: 3 Activities, 3 XML layouts, a Compose theme that no screen uses, and Gradle files.

## Part B. Adding the new code

The change set is a ready-made copy of the project's files. It already contains the package rename, so
**do not** rename the package inside Android Studio first.

1. Close Android Studio (or just stay in it and use the file manager; either works).
2. In the cloned project, **delete** the old source folders (they are replaced, not merged):
   - `app/src/main/java/com/example/quizapp`
   - `app/src/test/java/com/example/quizapp`
   - `app/src/androidTest/java/com/example/quizapp`
3. Copy from the change set into the project root, accepting overwrites:
   - `app/` (source, manifest, `build.gradle.kts`, `strings.xml`)
   - `settings.gradle.kts`
   - `README.md` and the `docs/` folder
4. Reopen the project. **File > Sync Project with Gradle Files**.
5. **Build > Clean Project**, then **Build > Rebuild Project**.
6. Run. Expected: splash -> home grid with 10 modules, bottom bar (Home, Compete, Profile).

### Why `com.example.cnc` and not just `cnc`
An Android application ID needs at least two dot-separated segments, so `cnc` alone is rejected. If you
prefer something else (for example `com.cnc.app`), use Android Studio's **Refactor > Rename** on the
`cnc` package folder, then update `namespace` and `applicationId` in `app/build.gradle.kts`.
Pick it before publishing: the applicationId cannot change after a Play Store release.

### Quick test checklist
- Home shows 10 cards in the requested order.
- DSA shows Python / Java / C++ tabs.
- Basic shows 8 topics; the six knowledge modules and Competitive show 10.
- Open any module (not Create Your Own) > Weekly Contest > answer 10 questions > leaderboard shows "(you)".
- Create Your Own: add a topic, restart the app, it is still there; Remove deletes it.
- Profile: save a display name, it appears on the leaderboard; the activity grid fills today's square.

## Part C. `main` vs `master`: what is going on

A Git **branch** is just a movable label pointing at a commit. `master` and `main` are only *names*:
there is nothing special about either. Older Git versions named the first branch `master`; GitHub and
newer Git default to `main`.

What I found in the original repo: it has **one branch, `main`**, one commit (message "jams"), and its
remote is called `origin`. So a clone of it starts on `main`, tracking `origin/main`.

If your screen shows `master` for "quizapp" and `main` for "codesncaves", the most likely explanations are:

1. **`QuizApp` is a project/folder name, not a branch.** In the original files the project name is `QuizApp`
   (`rootProject.name`); the change set renames it to `codesncaves`. Branch names appear in Android
   Studio's bottom-right **Git widget** and in the **Git tool window**, not in the Project panel.
2. **You have two separate folders**: an older local copy created with `git init` (branch `master`) and the
   clone (branch `main`). These are two independent repositories with different histories.

To see exactly where you stand, open **Terminal** in Android Studio and run:

```
git status
git branch -a
git remote -v
git log --oneline --all
```

Reading the output:
- `* main` = you are on `main`. `remotes/origin/main` = the copy on the remote called `origin`.
- `git remote -v` shows where `git push` will go. If it shows `github.com/Phoenix1808/Jams-26`, pushing goes to *that* repo, not yours.

**Recommendation:** use only the clone (the `main` one) as your working repository. Do not try to merge the
`master` copy into it; just copy any files you want. (Merging two unrelated histories needs
`--allow-unrelated-histories` and creates a confusing history.) If you want your branch to be called `main`
everywhere: `git branch -M main`.

## Part D. Pushing to your personal GitHub account

### Will there be a problem? Short answer: yes, three things to handle first

1. **The remote still points at the original repo.** `origin` is `Phoenix1808/Jams-26`. A push there will be
   rejected (HTTP 403) unless you are a collaborator on that repository, and if you *are* one it would push
   into the original project. Point the project at your own repo first (steps below).
2. **Ownership and licence.** The original has no `LICENSE`, which by default means all rights are reserved
   by its author, and the splash screen credits "GDG JSS". If `Phoenix1808` is you, or a teammate who agrees,
   there is no problem. If it is someone else's work, get their permission, keep the credit (the README does),
   or use GitHub's **Fork** instead so the link to the original is visible.
3. **Authentication.** GitHub no longer accepts account passwords for Git. Use Android Studio's GitHub login
   (**Settings > Version Control > GitHub > Add account**, browser sign-in) or a Personal Access Token.

Not a problem: `local.properties` (your SDK path) and build folders are already excluded by `.gitignore`, so no
secrets are committed. The package rename shows up in Git as deleted + added files, which is harmless.
The original commit author (`Phoenix1808`) stays visible in the history if you keep it; that is normal and fair credit.

### Steps (new empty repo on your account)

1. On github.com, **New repository**. Name it `codesncaves`. Leave **"Add a README / .gitignore / licence" unchecked** (the project already has them).
2. In Android Studio **Terminal** (project root):

```
git remote rename origin upstream          # keep a link to the original, harmless
git remote add origin https://github.com/<your-username>/codesncaves.git
git branch -M main
git add .
git commit -m "Add modules, weekly contests, leaderboard, profile; rename package to com.example.cnc"
git push -u origin main
```

3. Refresh your GitHub page: the code, `README.md` and `docs/` should appear.

Alternative with no commands: **Git > GitHub > Share Project on GitHub** in Android Studio creates the repo
under your account and sets `origin` for you (still run the `git remote rename`/commit steps if you want to keep `upstream`).

Later pushes are just: commit, then **Git > Push** (or `git push`).

### If something goes wrong
- `403` or "permission denied": the remote is still the original, or you are signed in to the wrong account. Run `git remote -v`.
- `rejected (fetch first)`: the GitHub repo is not empty. Either recreate it empty, or `git pull --rebase origin main` first.
- `unrelated histories`: you tried to combine two different repos; use only one working folder (Part C).
