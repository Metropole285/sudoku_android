Sudoku Android MVP — Engineering & Product Specification
Summary
Android Sudoku MVP targeting 1-month delivery with minimal team (1 FTE Android + 0.25 support). Two-tier subscription model (ad removal + premium puzzles), leaderboards, offline-first architecture. Firebase backend for auth/leaderboards/subscriptions. Target: <25 MB APK, <2s cold start, full offline gameplay. Conservative timeline assumes 80% feature completion; aggressive path requires cut scope or 1.5 FTE.

1. Product One-Pager
Elevator Pitch:
Classic Sudoku with clean Android Material 3 UI, offline play, global leaderboards, and premium "variant" puzzles (Killer, X-Sudoku) via subscription. Free tier supported by interstitial ads (post-game, post-errors, skip-hint).
Core Value:

Casual players: Quick, offline-capable brain training with optional competitive leaderboards.
Enthusiasts: Advanced variants behind paywall drive engagement and ARPU.
Business: Dual monetization (ads + subscriptions) with minimal infrastructure cost (Firebase free tier + Blaze).

Key Features (MVP):

Puzzle generation (Easy/Medium/Hard) with backtracking solver validation.
Timer, hint system (1 free/puzzle), undo/redo.
Leaderboards (daily/weekly/all-time by difficulty).
AdMob interstitials (3 triggers: post-3-games, post-3-errors, skip-hint).
In-app subscriptions (Tier 1: ad-free; Tier 2: ad-free + variants).
Offline-first: sync leaderboards when online.


2. Assumptions
IDAssumptionImpactA1Target audience: 18–45, casual mobile gamers, 60% Android 10+.Dictates UI complexity, API levels (minSdk 26).A2User session: 5–10 min, 1–3 games/session.Influences ad frequency tuning.A3Leaderboard participation: 20–30% of DAU.Justifies leaderboard complexity.A4Subscription conversion: 2–5% (Tier 1), 0.5–1% (Tier 2).Revenue projections, paywall design priority.A5Firebase free tier sufficient for <10k DAU; Blaze costs <$50/mo up to 50k DAU.Backend cost model.A6AdMob CPM $3–$8 (geography-dependent).Ad revenue baseline.A7Team: 1 Android dev (senior/mid), 0.25 FTE PM, 0.1 FTE QA, 0.1 FTE backend config.Resourcing and velocity.A8Variant puzzles (Killer, X-Sudoku) add ~20% dev time if rules engine modular.Tier 2 feature feasibility.A9Play Store review: 3–7 days post-submission.Release buffer in week 4.A10GDPR compliance: minimal PII (email via Firebase Auth, anonymized leaderboard names).Privacy implementation scope.

3. MVP Feature List (Must-Ship in 4 Weeks)

Puzzle Play: Generate/validate 4×4, 9×9 Easy/Medium/Hard. Display grid, input numbers, validate.
Timer & Scoring: Track solve time; score = (difficulty_multiplier × 1000) / time_seconds.
Hints: 1 free hint/puzzle (reveal cell); additional hints = watch ad or subscription.
Undo/Redo: Last 10 moves.
Leaderboards: Global daily/weekly/all-time per difficulty. Display top 100.
Ads: Interstitials after 3 games OR 3 errors OR skipping hint (AdMob).
Subscriptions: Tier 1 ($2.99/mo) removes ads; Tier 2 ($4.99/mo) adds Killer Sudoku variant.
Offline Mode: Play/save progress; sync leaderboards on connectivity.
Settings: Sound on/off, theme (light/dark), reset statistics.
Onboarding: 1-screen tutorial (swipe-through) on first launch.

Out of Scope (Post-MVP):

Multiplayer/head-to-head.
Social sharing (unless leaderboard name = social handle).
Advanced analytics dashboards (use Firebase Analytics basic events).
Localization (English only for MVP).


4. User Personas & Key Scenarios
Persona 1: Casual Chris (Primary)

Demographics: 25–40, commutes 30 min, plays mobile games casually.
Goals: Kill time, light mental exercise, avoid pay-to-win.
Pain Points: Too many intrusive ads, complex UIs.
Scenario: Opens app on subway, plays 1 Medium puzzle (7 min), sees ad post-game, closes app. Returns next day for leaderboard rank check.

Persona 2: Competitive Casey (Secondary, 20% of users)

Demographics: 28–50, puzzle enthusiast, has iOS Sudoku app.
Goals: Climb leaderboards, solve Hard puzzles fast, unlock variants.
Pain Points: Ads interrupt flow, wants advanced challenges.
Scenario: Subscribes Tier 2 ($4.99/mo) after 1 week, plays Killer Sudoku daily, tracks weekly rank. Churn risk if leaderboard inactive.

Persona 3: Budget Beth (Assumption-based, 15% of users)

Demographics: 18–25, limited budget, ad-tolerant.
Goals: Free entertainment, no subscriptions.
Pain Points: Too frequent ads = uninstall.
Scenario: Plays 2–3 Easy puzzles/day, watches ads for hints, stays free indefinitely. Key monetization via ad impressions.


5. Feature Prioritization (MoSCoW)
Must Have

Puzzle generation/validation (9×9, 3 difficulties): Core gameplay.
Timer, basic UI (grid input, number pad): Minimum viable UX.
Leaderboards (Firebase): Stated requirement.
Ads (3 triggers): Monetization baseline.
Subscription Tier 1 (ad removal): Revenue diversification.
Offline play: Differentiator vs. web apps.

Should Have

Subscription Tier 2 (Killer Sudoku): ARPU boost, feasible if rules engine modular.
Undo/Redo: QOL, reduces user frustration = retention.
Hints (1 free): Accessibility, reduces rage-quits.
Dark mode: Modern expectation, low effort (Material 3).

Could Have

Weekly/daily challenges: Engagement, but requires backend cron + push notifications (defer to week 5).
4×4 puzzles: Accessibility for beginners, low priority if Easy 9×9 sufficient.

Won't Have (This Release)

X-Sudoku, Samurai variants: Tier 2 limited to Killer only for MVP.
Achievements/badges: Backend complexity, defer.
Localization: English-only reduces QA/PM overhead by 50%.


6. UX Design
Screen List (10 screens)

Splash Screen: App logo, 1.5s.
Onboarding (First Launch): Single-page swipe: "Tap cell → select number → solve!"
Home/Menu: "New Game" (Easy/Medium/Hard), "Continue", "Leaderboards", "Settings", "Subscribe".
Game Screen: 9×9 grid, number pad (1–9 + erase), timer, hint button, pause, undo/redo icons.
Pause Overlay: Resume, Restart, Main Menu, Settings.
Win Screen: Time, score, "Submit to Leaderboard" (if online), "New Game", "Share" (out_of_scope_for_1_month).
Leaderboards: Tabs (Daily/Weekly/All-Time), dropdown (Easy/Medium/Hard), scrollable list (rank, name, time, score).
Settings: Toggle sound, theme (light/dark/auto), "Clear Data", "Rate Us", "Privacy Policy".
Subscription Paywall: Compare Tier 1 vs Tier 2 features, "Subscribe" buttons, "Restore Purchases".
Ad Interstitial (Full-Screen): AdMob standard, 5s countdown to close.

Text Wireframes (Key Screens)
Game Screen:
┌─────────────────────────────┐
│  [Pause] Timer: 05:32  [?]  │ ← Hint button
├─────────────────────────────┤
│    3 │ 5 │ 7 │ 1 │ 9 │ ...  │
│   ───┼───┼───┼───┼───┼───   │
│    8 │ _ │ 2 │ ...           │ ← Grid (9×9), empty = editable
│   ...                        │
├─────────────────────────────┤
│  [1][2][3][4][5][6][7][8][9]│ ← Number pad
│           [Erase]            │
│  [Undo] [Redo]               │
└─────────────────────────────┘
Leaderboards:
┌─────────────────────────────┐
│ [Daily][Weekly][All-Time]   │
│ Difficulty: [Easy ▼]        │
├─────────────────────────────┤
│  1. Alice    03:24  1200pts │
│  2. Bob      03:51  1100pts │
│  3. You      04:12  1050pts │ ← User row highlighted
│  ...                         │
└─────────────────────────────┘
Key Interactions

Ad Triggers:

After 3 completed games: Interstitial on "New Game" press.
After 3 incorrect inputs: Interstitial on next correct input.
Hint button (if free hint used): Show ad, then reveal hint.


Subscription Flow: Tap "Subscribe" → Paywall screen → Google Play Billing dialog → Purchase → Remove ads + unlock Tier 2 (if applicable).
Offline Behavior: Leaderboard button disabled (greyed) if offline; show toast "Connect to view leaderboards". Game play unaffected.


7. Technical Architecture
Pattern: MVVM (Model-View-ViewModel)
Why: Clean separation UI/logic, testable ViewModels, native Android (Jetpack ViewModel + LiveData/Flow). Compose-friendly.
Modules (Single-module MVP, logical layers)
:app
├── ui (Composables, Activities, theme)
├── viewmodel (GameViewModel, LeaderboardViewModel, SubscriptionViewModel)
├── domain (use cases: GeneratePuzzleUseCase, ValidateSolutionUseCase, SubmitScoreUseCase)
├── data
│   ├── repository (PuzzleRepository, LeaderboardRepository, SubscriptionRepository)
│   ├── local (Room: GameStateEntity, SettingsEntity)
│   ├── remote (Firebase Firestore: leaderboards collection, Auth)
├── ads (AdManager wrapper for AdMob)
├── billing (BillingManager wrapper for Play Billing Library)
└── utils (Constants, Extensions)
Core Components
1. Puzzle Generator/Solver

Algorithm: Backtracking solver (validates uniqueness). Generator: Fill diagonal 3×3 boxes → solve → remove cells (difficulty = # removed).
Library: Custom Kotlin implementation (100–150 LOC). Why: No mature open-source Kotlin Sudoku lib; simple enough to own.
Validation: Every generated puzzle solved by backtracker to ensure single solution.

2. Persistence (Room)

Entities:

GameStateEntity: id, grid (JSON string of 81 ints), difficulty, timer, moves (undo stack JSON), timestamp.
SettingsEntity: theme, soundEnabled, statisticsJson (games played, win rate).


DAO: GameStateDao.insertOrUpdate(), getLatestGame(), deleteAll().
Why Room: Offline-first, minimal boilerplate, type-safe.

3. Offline-First Logic

Play: All game state in Room; no network required.
Leaderboards: On win, queue score locally. Background WorkManager task syncs to Firestore when online (exponential backoff).
Subscriptions: Google Play Billing verifies purchases; cache entitlements in SharedPreferences + re-validate on app start if online.

4. Backend (Firebase)
Services Used:

Firebase Auth: Anonymous sign-in (for leaderboard user ID). Optional email sign-in (future).
Firestore:

Collection: leaderboards/{difficulty}/{period}/scores (documents: {userId, displayName, time, score, timestamp}).
Indexes: Composite on difficulty + period + score (descending).


Cloud Functions (optional, defer if time-constrained): Cron to reset daily/weekly leaderboards (or client-side filter by timestamp).
Firebase Analytics: Track events (game_start, game_complete, ad_shown, subscription_purchased).

Why Firebase: Free tier supports <10k DAU; integrated Auth/Firestore/Analytics; no server maintenance.
Alternative (if Firebase cost concern): Supabase (Postgres + Auth + Realtime) on free tier, or simple REST API on Render.com free tier. Firebase preferred for BOM integration.
API Contract (Firestore):

POST /leaderboards/{difficulty}/{period}: Submit score (userId, displayName, time, score).
GET /leaderboards/{difficulty}/{period}?limit=100: Fetch top 100.
Security Rules: Authenticated users can write own scores, read all.


8. Technology Stack
ComponentLibrary/ToolWhyLanguageKotlin 1.9+Android standard, concise, coroutines.UIJetpack ComposeModern declarative UI, faster iteration than XML.ArchitectureJetpack ViewModel + NavigationMVVM, lifecycle-aware, type-safe navigation.AsyncCoroutines + FlowStructured concurrency, reactive streams.DIHiltCompile-time DI, less boilerplate than Dagger.PersistenceRoom 2.6+Type-safe SQL, offline-first.NetworkRetrofit + OkHttp (if REST)Efficient HTTP, interceptors for logging. Firebase SDK if Firestore.AdsGoogle AdMob SDKHigh CPM, easy integration, mediation support.BillingGoogle Play Billing Library 6+Required for subscriptions, server-side verification.BackendFirebase (Auth, Firestore, Analytics)Free tier, integrated BOM, minimal ops.LeaderboardsFirestore + composite indexesReal-time, scalable, no custom backend code.TestingJUnit 5, MockK, Turbine (Flow testing)Unit tests for ViewModels/UseCases.UI TestingCompose UI Test, Espresso (fallback)Automated acceptance tests.CI/CDGitHub ActionsFree for public repos, YAML-based, APK signing.Crash ReportingFirebase CrashlyticsFree, integrates with Analytics.LoggingTimberMinimal overhead, tag-based.

9. CI/CD Pipeline
Git Workflow

Branches: main (production), develop (staging), feature branches (feature/ads-integration).
PR Checks: Lint (ktlint), unit tests, build APK.

Pipeline (GitHub Actions YAML)
yamlname: Android CI

on:
  push:
    branches: [main, develop]
  pull_request:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Lint
        run: ./gradlew ktlintCheck
      - name: Unit Tests
        run: ./gradlew test
      - name: Build APK
        run: ./gradlew assembleRelease
      - name: Sign APK
        uses: r0adkll/sign-android-release@v1
        with:
          releaseDirectory: app/build/outputs/apk/release
          signingKeyBase64: ${{ secrets.SIGNING_KEY }}
          alias: ${{ secrets.KEY_ALIAS }}
          keyStorePassword: ${{ secrets.KEY_STORE_PASSWORD }}
      - name: Upload to Play Store (Internal Track)
        if: github.ref == 'refs/heads/main'
        uses: r0adkll/upload-google-play@v1
        with:
          serviceAccountJsonPlainText: ${{ secrets.PLAY_SERVICE_ACCOUNT }}
          packageName: com.sudoku.app
          releaseFiles: app/build/outputs/apk/release/*.apk
          track: internal
Secrets Setup: Store signing key, Play Console service account JSON in GitHub Secrets.
Release Process:

Merge develop → main (triggers CI).
CI uploads APK to Internal Testing track.
Manual promotion to Beta/Production in Play Console.


10. QA & Test Plan
Unit Tests (Target: 70% coverage)

GameViewModel: Puzzle generation, validation, timer logic, undo/redo.
PuzzleRepository: Room insert/fetch, offline sync queueing.
SubscriptionRepository: Entitlement caching, purchase validation mock.

Integration Tests

Room + Repository: Insert game state, fetch, verify JSON parsing.
Firebase (emulator): Submit score to Firestore emulator, query leaderboard.

UI Tests (Compose UI Test)

Game Flow: Launch app → select Easy → input numbers → complete puzzle → verify win screen.
Ad Trigger: Play 3 games → verify interstitial shown on 4th game start.
Subscription: Tap Subscribe → mock Play Billing response → verify ads hidden.

Acceptance Criteria (MVP Features)
FeatureCriteriaPuzzle GenerationGenerate 100 Easy/Medium/Hard puzzles in <5s each; all solvable with unique solution.TimerAccurate to ±1s over 10 min game.Hints1 free hint reveals correct cell; additional hints require ad watch or subscription.AdsInterstitial shown after 3 games, 3 errors, or hint skip. Not shown for Tier 1/2 subscribers.LeaderboardsDisplay top 100; user's rank highlighted. Syncs within 30s when online.SubscriptionsTier 1 removes ads; Tier 2 unlocks Killer Sudoku. Purchases persist across reinstalls.Offline PlayNo network errors during gameplay; leaderboard disabled when offline (toast shown).
Manual QA Checklist (Week 4)

 Test on Android 10, 12, 14 (emulator + 1 physical device).
 Rotate screen during game (state persists).
 Force-close app mid-game (resume on reopen).
 Airplane mode: play game, enable network, verify leaderboard sync.
 Subscription purchase flow (test account, sandbox).
 Ad frequency tuning: play 10 games, count interstitials.


11. Non-Functional Requirements
MetricTargetMeasurementUI Latency≤100 ms (tap → response)Systrace, Compose Layout Inspector.Cold Start≤2s (splash → home screen) on mid-range device (e.g., Pixel 5a).adb shell am start -W, Firebase Performance Monitoring.APK Size≤25 MB (base APK, no splits).Gradle build output, Android Size Analyzer.Memory≤150 MB on 2 GB RAM device.Android Profiler (heap dump during gameplay).Offline Support100% gameplay; leaderboards graceful degradation.Manual test: airplane mode.Crash-Free Rate≥99.5% (Firebase Crashlytics).Crashlytics dashboard post-launch.Battery<5% drain per 30 min session (screen-on time normalized).Battery Historian.
Optimizations:

ProGuard/R8 shrinking enabled.
WebP images (50% smaller than PNG).
Lazy-load leaderboard data (pagination: 100 items/fetch).
Ads preload on game completion (not mid-game).


12. Monetization & KPI
Implementation
Ads (AdMob)

Integration: Singleton AdManager wraps InterstitialAd.
Triggers: Counters in GameViewModel (games_played, errors_count). On threshold, call AdManager.show().
Frequency Cap: Max 1 ad per 5 min (AdMob setting).
Test Ads: Use AdMob test IDs during dev; switch to production IDs on release.

Subscriptions (Play Billing Library 6)

Products: tier1_monthly ($2.99), tier2_monthly ($4.99).
Entitlement Check: BillingManager.isSubscribed(productId) queries Play Store; caches result in SharedPreferences (TTL: 24h).
Server Verification: Optional (defer to post-MVP): send purchase token to Firebase Function → verify with Google Play Developer API → store entitlement in Firestore.
Restore Purchases: Button in Paywall screen calls BillingClient.queryPurchasesAsync().

Leaderboards

Free Feature: No paywall. Drives engagement → ad impressions → Tier 1 conversion (ad fatigue).
Ranking Logic: Sort by score DESC, time ASC (higher score = better; tiebreaker = faster time).
Display Names: Firebase Auth displayName (default: "Player12345"); editable in Settings (future).

KPI Measurement Methodology
Metrics to Track (Firebase Analytics + Custom Events):
MetricDefinitionMeasurementDAUUnique users opening app per day.Firebase Analytics active_users event.Retention D1/D7% users returning after 1/7 days.Firebase Analytics cohort analysis.Games/SessionAvg puzzles played per session.Custom event game_complete count / session_start count.Ad ImpressionsTotal ads shown.AdMob dashboard + custom event ad_shown.Ad CTRClick-through rate.AdMob dashboard.Subscription Conversion% DAU purchasing Tier 1/2.Custom events subscription_purchased / DAU.Churn Rate% subscribers canceling per month.Play Console subscription reports.ARPUAvg revenue per user (monthly).(Ad revenue + subscription revenue) / MAU.
Scenario Modeling (Methodology, Not Forecasts):
To estimate revenue, collect:

DAU Projection: Use Firebase Analytics post-launch (week 2 data extrapolated).
Ad Revenue: Impressions × CPM ÷ 1000. Get CPM from AdMob dashboard (geography-specific).
Subscription Revenue: Subscribers × Price. Conversion rate from A/B test (vary paywall copy/pricing).

Example Framework (Placeholder Values):
ScenarioDAUAd Impr/User/DayCPMTier 1 ConvTier 2 ConvMonthly RevenueConservative5,0000.5$32%0.3%Calculate post-launchBase15,0000.8$53%0.7%Calculate post-launchAggressive50,0001.2$75%1.5%Calculate post-launch
Action: Replace placeholders with real data after 2 weeks live. Adjust ad frequency/pricing based on churn/conversion curves.

13. Four-Week Roadmap
Week 1: Core Gameplay (1.5 FTE total)
Android Dev (1.0 FTE):

Project setup (Compose, Hilt, Room, Firebase SDK).
Puzzle generator/solver implementation + unit tests.
Game UI (grid, number pad, timer) + ViewModel.
Room schema (GameStateEntity, SettingsEntity).

PM (0.25 FTE): Finalize wireframes, write user stories.
Backend (0.1 FTE): Firebase project setup, Firestore schema, Auth config.
Deliverables:

Playable 9×9 Sudoku (Easy/Medium/Hard).
Timer functional.
Save/load game state (Room).

Estimate: 40 hours Android dev, 10 hours PM, 4 hours backend.

Week 2: Monetization + Leaderboards (1.4 FTE total)
Android Dev (1.0 FTE):

AdMob integration (interstitials, 3 triggers).
Play Billing Library integration (Tier 1/2 products).
Leaderboard UI + Firestore sync (submit score, fetch top 100).
Offline queue (WorkManager for leaderboard sync).

QA (0.1 FTE): Ad trigger testing, subscription sandbox flow.
Backend (0.1 FTE): Firestore security rules, composite indexes.
Deliverables:

Ads shown per triggers (not for subscribers).
Subscriptions purchasable (test mode).
Leaderboards live (global daily/weekly/all-time).

Estimate: 40 hours Android dev, 4 hours QA, 4 hours backend.

Week 3: Polish + Tier 2 Feature (1.3 FTE total)
Android Dev (1.0 FTE):

Killer Sudoku variant (cage constraints) + generator.
Hints system (1 free + ad-gated).
Undo/Redo (last 10 moves).
Dark mode, settings screen.
Onboarding screen (first launch).

QA (0.15 FTE): Regression tests, UI/UX flow validation.
PM (0.15 FTE): Play Store assets (screenshots, description).
Deliverables:

Tier 2 unlocked (Killer Sudoku playable).
Hints functional.
UI polished (themes, animations).

Estimate: 40 hours Android dev, 6 hours QA, 6 hours PM.

Week 4: QA + Release (1.2 FTE total)
Android Dev (0.7 FTE):

Bug fixes from QA.
CI/CD pipeline setup (GitHub Actions).
ProGuard rules, APK size optimization.

QA (0.3 FTE): Full regression (manual + automated UI tests).
PM (0.2 FTE): Play Store listing finalization, release checklist.
Deliverables:

Alpha build on Internal Testing track (Play Console).
All P0/P1 bugs resolved.
Release candidate submitted for review (by day 26).

Estimate: 28 hours Android dev, 12 hours QA, 8 hours PM.
Buffer: Days 27–28 for Play Store review (3–7 days typical).

14. Resourcing
RoleFTEDurationResponsibilitiesAndroid Developer1.04 weeksAll coding, CI/CD, technical decisions. Ideally senior (3+ years Kotlin/Compose).Product Manager0.25Weeks 1, 3–4Wireframes, user stories, Play Store listing, release coordination.QA Engineer0.15Weeks 2–4Ad/subscription testing, regression, manual QA checklist.Backend Engineer0.1Weeks 1–2Firebase setup, Firestore rules, indexes. Can be Android dev if Firebase-experienced.
Total Effort: ~5.5 person-weeks over 4 calendar weeks (1.375 FTE average). Conservative for 1 dev + part-time support.
Risk Mitigation: If Android dev unavailable full-time, cut Killer Sudoku (Tier 2 = ad-free only) to save ~8 hours.

15. Play Store Release Checklist

 App complies with Google Play policies (no restricted content, COPPA if targeting <13).
 Privacy policy URL hosted (required for data collection: ads, analytics).
 Signed APK with production keystore (upload key, not debug).
 App icon (512×512 PNG), feature graphic (1024×500).
 Screenshots (min 2, max 8) for phone + tablet.
 Short description (<80 chars), full description (<4000 chars).
 Content rating questionnaire completed (IARC).
 AdMob production ad unit IDs (replace test IDs).
 Firebase project in production mode (disable emulator, Auth methods enabled).
 ProGuard rules tested (no crashes from obfuscation).
 APK size <25 MB (verify Android Size Analyzer).
 Test on ≥3 devices (Android 10, 12, 14).
 Crashlytics enabled, deobfuscation mapping uploaded.
 GDPR consent flow (if EU users): AdMob UMP SDK for consent.
 Subscription server-side verification (optional for MVP, recommended post-launch).
 Release notes written (English, <500 chars).


16. Risks & Mitigation
RiskProbabilityImpactMitigationPuzzle generator produces invalid puzzlesMediumHighUnit test 1000 puzzles pre-launch; validate uniqueness via solver. Fallback: pre-generated puzzle DB (500 puzzles).Firebase free tier exceededLow (<10k DAU)MediumMonitor Firestore usage (Blaze auto-upgrade). Cache leaderboards aggressively (1h TTL).Ad revenue below projectionsHigh (geography/CPM variance)MediumA/B test ad frequency (2 vs 3 games). Mediation (Facebook Audience Network) post-MVP.Subscription conversion <1%MediumHighA/B test paywall (week 3 vs week 1 trigger). Highlight leaderboard rank in paywall ("Top 10% players subscribe").Play Store review rejectionLow (if policy-compliant)HighSubmit for review by day 24 (3-day buffer). Pre-check: privacy policy, content rating, ad disclosures.1 dev unavailable (sick/leave)MediumCriticalCut Tier 2 (Killer Sudoku) → ad-free only. 20% scope reduction = still MVP viable.Google Play Billing bug (sandbox → prod)LowMediumTest with real purchase (refund immediately). Implement restore purchases early (week 2).Leaderboard cheating (fake scores)Medium (post-launch)LowServer-side validation (Cloud Function checks solve time plausibility). Defer to week 5 if time-constrained.

17. A/B Testing & Analytics
A/B Test Plan (Post-MVP, Week 5+)
TestVariantsMetricDurationAd FrequencyA: 3 games, B: 2 games, C: 4 gamesRetention D7, ad revenue per user.2 weeksPaywall TimingA: After 5 games, B: After 1st game, C: After leaderboard viewSubscription conversion (%).2 weeksTier 2 PricingA: $4.99, B: $3.99, C: $5.99Tier 2 conversion, ARPU.2 weeksHint Ad TriggerA: Ad mandatory, B: Ad optional (can cancel)Hint usage rate, ad impressions.1 week
Tool: Firebase Remote Config + Firebase A/B Testing (free).
Initial Analytics Events
kotlin// Custom events (Firebase Analytics)
analytics.logEvent("game_start", Bundle().apply {
    putString("difficulty", "medium")
})
analytics.logEvent("game_complete", Bundle().apply {
    putString("difficulty", "medium")
    putInt("time_seconds", 342)
    putInt("hints_used", 1)
})
analytics.logEvent("ad_shown", Bundle().apply {
    putString("trigger", "post_3_games") // or "post_3_errors", "hint_skip"
})
analytics.logEvent("subscription_purchased", Bundle().apply {
    putString("tier", "tier1") // or "tier2"
})
analytics.logEvent("leaderboard_view", Bundle())
```

**Funnels to Track:**
1. App open → game start → game complete (completion rate).
2. Game complete → ad shown → ad clicked (CTR).
3. Paywall view → subscription purchased (conversion).
4. Leaderboard view → game start (engagement loop).

---

## 18. Deliverables (Repository Contents at Release)

- [ ] `README.md` (setup instructions, architecture overview).
- [ ] `/app` source code (Kotlin, Compose UI).
- [ ] `/gradle` build scripts (Gradle 8+, Kotlin DSL).
- [ ] `.github/workflows/ci.yml` (CI/CD pipeline).
- [ ] `google-services.json` (Firebase config, gitignored, template provided).
- [ ] `proguard-rules.pro` (R8 obfuscation rules).
- [ ] Unit tests (`/app/src/test`), UI tests (`/app/src/androidTest`).
- [ ] Privacy policy (Markdown or hosted URL).
- [ ] Play Store assets (`/assets/playstore`: icon, screenshots, feature graphic).
- [ ] Keystore instructions (`keystore.properties.template`).
- [ ] Firestore security rules (`firestore.rules`).
- [ ] Release notes template (`RELEASE_NOTES.md`).
- [ ] User manual (optional, 1-pager PDF or in-app help).

---

## 19. Project Structure Example
```
SudokuApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sudoku/app/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── theme/
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   ├── Theme.kt
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── GameScreen.kt
│   │   │   │   │   │   ├── LeaderboardScreen.kt
│   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── GameViewModel.kt
│   │   │   │   │   ├── LeaderboardViewModel.kt
│   │   │   │   ├── domain/
│   │   │   │   │   ├── model/ (Puzzle, Cell, Leaderboard)
│   │   │   │   │   ├── usecase/
│   │   │   │   │   │   ├── GeneratePuzzleUseCase.kt
│   │   │   │   │   │   ├── ValidateSolutionUseCase.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── PuzzleRepository.kt
│   │   │   │   │   │   ├── LeaderboardRepository.kt
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   │   ├── dao/ (GameStateDao, SettingsDao)
│   │   │   │   │   │   ├── entity/ (GameStateEntity)
│   │   │   │   │   ├── remote/
│   │   │   │   │   │   ├── FirestoreService.kt
│   │   │   │   ├── ads/ (AdManager.kt)
│   │   │   │   ├── billing/ (BillingManager.kt)
│   │   │   │   ├── di/ (AppModule.kt - Hilt)
│   │   │   │   ├── utils/ (Constants.kt, Extensions.kt)
│   │   │   │   ├── SudokuApplication.kt
│   │   │   ├── res/ (layouts, drawables, strings)
│   │   │   ├── AndroidManifest.xml
│   │   ├── test/ (unit tests)
│   │   ├── androidTest/ (UI tests)
│   ├── build.gradle.kts
├── build.gradle.kts (root)
├── settings.gradle.kts
├── google-services.json (gitignored)
├── .github/workflows/ci.yml
├── README.md

20. Code Example: Sudoku Generator (Kotlin)
kotlin/**
 * Generates a valid 9x9 Sudoku puzzle with unique solution.
 * Uses backtracking solver to validate uniqueness.
 */
class SudokuGenerator {
    private val solver = SudokuSolver()

    fun generate(difficulty: Difficulty): IntArray {
        // Initialize 9x9 grid (81 cells, 0 = empty)
        val grid = IntArray(81) { 0 }

        // Fill diagonal 3x3 boxes (independent, no conflicts)
        fillDiagonalBoxes(grid)

        // Solve the rest using backtracking
        solver.solve(grid)

        // Remove cells based on difficulty (Easy: 35, Medium: 45, Hard: 55)
        val cellsToRemove = when (difficulty) {
            Difficulty.EASY -> 35
            Difficulty.MEDIUM -> 45
            Difficulty.HARD -> 55
        }
        removeCells(grid, cellsToRemove)

        return grid
    }

    private fun fillDiagonalBoxes(grid: IntArray) {
        for (box in 0..2) {
            val nums = (1..9).shuffled()
            for (i in 0..8) {
                val row = box * 3 + i / 3
                val col = box * 3 + i % 3
                grid[row * 9 + col] = nums[i]
            }
        }
    }

    private fun removeCells(grid: IntArray, count: Int) {
        val positions = (0..80).shuffled().take(count)
        positions.forEach { grid[it] = 0 }

        // Validate uniqueness: ensure only 1 solution exists
        val copy = grid.clone()
        if (!solver.hasUniqueSolution(copy)) {
            // Rare case: re-generate if multiple solutions
            generate(Difficulty.EASY) // Retry
        }
    }
}

enum class Difficulty { EASY, MEDIUM, HARD }
Explanation:

fillDiagonalBoxes: Fills 3 independent 3×3 boxes (top-left, center, bottom-right) with shuffled 1–9. No conflicts possible.
solver.solve: Completes the grid using backtracking (validates row/col/box constraints).
removeCells: Randomly removes N cells; validates resulting puzzle has unique solution via hasUniqueSolution() (counts solutions, ensures == 1).
Difficulty: More removed cells = harder puzzle. Easy: 35 blanks (~61% filled), Hard: 55 blanks (~39% filled).


Next Steps

Finalize Team: Confirm Android dev availability (1.0 FTE) + part-time PM/QA/backend support.
Setup Firebase Project: Create project, enable Auth (anonymous), Firestore, Analytics; download google-services.json.
Week 1 Kickoff: Clone starter repo (Compose + Hilt + Room template), implement puzzle generator + core UI.
Data Collection (Parallel to Dev):

Research competitor apps (pricing, ad frequency, reviews).
Define target CPM range (AdMob dashboard regional data).
Draft Play Store copy + screenshots mockups.


Risk Review (End of Week 2): Assess if Killer Sudoku (Tier 2) on track; if behind, defer to post-MVP and simplify Tier 2 to ad-free only.


Document Version: 1.0
Last Updated: 2026-02-08
Owner: Principal Mobile Architect
Next Review: End of Week 2 (milestone checkpoint)
