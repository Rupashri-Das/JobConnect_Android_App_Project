package com.rupashri.jobconnect.ui

/**
 * Defines the sealed interface for all top-level destinations (screens) in the app.
 */
sealed interface JobConnectDestination {
    // Defines the route string (used by NavController)
    val route: String
}

// 1. List Screen (The main Job Search view)
object JobListDestination : JobConnectDestination {
    override val route = "job_list"
}

// 2. Saved Jobs Screen (The new third screen for local persistence)
object SavedJobsDestination : JobConnectDestination {
    override val route = "saved_jobs"

    // We keep the old argument structure just for consistency in the NavHost,
    // although we won't use it on this screen.
    const val jobIdArg = "job_id"
    val routeWithArgs = "$route?$jobIdArg={$jobIdArg}"
}

// 3. Detail Screen (View a single job's details)
object JobDetailDestination : JobConnectDestination {
    override val route = "job_detail"

    // Argument name for passing the job ID
    const val jobIdArg = "job_id"

    // Route format that requires the job ID argument.
    val routeWithArgs = "$route/{$jobIdArg}"
}
