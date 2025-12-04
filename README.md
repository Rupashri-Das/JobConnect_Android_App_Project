# JobConnect_Android_App_Project
JobConnect is a simple student-focused android app built with Kotlin and Jetpack Compose. It lets users see the job list and description of the job and save or unsave their favorites locally using an MVVM architecture with Room database persistence. With this, they can see the simulated job postings (using local, hardcoded data) and manage their favorite opportunities using local persistence.

## Features
**Job Post List:** Displays all available job opportunities.
**Job Detail View:** Shows full job description and details.
**Save/Unsave (Favorites):** Allows users to save jobs to local storage (Room database).
**Saved Jobs List:** Dedicated screen to view all favorited jobs.
**State Management:** Uses ViewModel and Kotlin Flow/StateFlow.
**UI/UX:** Implements Material 3 design and dark mode support.
**Testing:** Includes at least one Unit Test (`JobDetailViewModelTest`).

## Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **UI Framework:** Jetpack Compose
- **Data Persistence:** Room Persistence Library
- **Navigation:** Compose Navigation
- **Tools:** JUnit, Mockito (for Unit Testing)

## How to Run
1. Clone the repository to your local machine.
2. Open the project in **Android Studio** (Narwhal 2025.1.4 or newer).
3. Build and run the `app` module on an **emulator** or **physical device** (API 26+).

