package com.rupashri.jobconnect.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

/**
 * Main Composable function that handles navigation between all screens.
 */
@Composable
fun JobConnectNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // NavHost defines the available screens and the starting destination.
    NavHost(
        navController = navController,
        startDestination = JobListDestination.route, // Start on the Job Search List
        modifier = modifier
    ) {
        // 1. Job List Screen (Job Search Results)
        composable(route = JobListDestination.route) {
            JobListScreen(
                onJobClick = { jobId ->
                    // Navigate to Job Detail Screen when a job is clicked
                    navController.navigate("${JobDetailDestination.route}/${jobId}")
                },
                onAddJobClick = {
                    // Navigate to the Saved Jobs List when the FAB is clicked
                    navController.navigate(SavedJobsDestination.route)
                }
            )
        }

        // 2. Saved Jobs Screen (The third required screen showing local persistence)
        composable(
            route = SavedJobsDestination.routeWithArgs,
            arguments = listOf(navArgument(SavedJobsDestination.jobIdArg) {
                type = NavType.IntType
                defaultValue = 0
                nullable = false
            })
        ) {
            SavedJobsScreen(
                onNavigateBack = {
                    navController.popBackStack() // Go back to the search list
                },
                onJobClick = { jobId ->
                    // Navigate to Job Detail Screen (if a saved job is clicked)
                    navController.navigate("${JobDetailDestination.route}/${jobId}")
                }
            )
        }

        // 3. Job Detail Screen (Actual Screen Implemented)
        composable(
            route = JobDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(JobDetailDestination.jobIdArg) {
                type = NavType.IntType
                nullable = false
            })
        ) {
            JobDetailScreen(
                onNavigateBack = {
                    navController.popBackStack() // Go back to the previous screen
                }
            )
        }
    }
}
