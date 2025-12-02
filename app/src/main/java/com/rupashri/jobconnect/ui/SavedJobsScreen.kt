package com.rupashri.jobconnect.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rupashri.jobconnect.data.JobPost
import com.rupashri.jobconnect.viewmodel.SavedJobsViewModel
import com.rupashri.jobconnect.viewmodel.JobViewModelFactory

/**
 * The screen displaying all job posts saved locally by the user.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedJobsScreen(
    onNavigateBack: () -> Unit,
    onJobClick: (Int) -> Unit, // Navigate to job detail screen
    modifier: Modifier = Modifier,
    // Inject ViewModel using the Factory
    viewModel: SavedJobsViewModel = viewModel(factory = JobViewModelFactory.SavedJobsFactory)
) {
    // Collect the live list of saved jobs from the Room database
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Saved Jobs") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.savedJobPosts.isEmpty() -> Text(
                    "You haven't saved any jobs yet.",
                    style = MaterialTheme.typography.titleMedium
                )
                else -> SavedJobsContent(
                    savedJobPosts = uiState.savedJobPosts,
                    onJobClick = onJobClick
                )
            }
        }
    }
}

@Composable
private fun SavedJobsContent(
    savedJobPosts: List<JobPost>,
    onJobClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        // Reuse the padding style from the search list
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Display saved job cards. We can reuse the JobPostCard from JobListScreen
        items(items = savedJobPosts, key = { it.id }) { jobPost ->
            JobPostCard(jobPost = jobPost, onJobClick = onJobClick)
        }
    }
}
// Note: JobPostCard is defined in JobListScreen.kt and is accessible if its visibility is public.
// If you encounter an error "Unresolved reference: JobPostCard", we will move it to a common file.
