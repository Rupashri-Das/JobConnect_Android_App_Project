package com.rupashri.jobconnect.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.rupashri.jobconnect.viewmodel.JobListViewModel
import com.rupashri.jobconnect.viewmodel.JobViewModelFactory

/**
 * The main screen displaying the list of all job posts (Search Results from FakeDataSource).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(
    onJobClick: (Int) -> Unit, // Navigate to job detail screen
    onAddJobClick: () -> Unit, // Navigate to the Saved Jobs screen
    modifier: Modifier = Modifier,
    // Inject ViewModel using the Factory we created
    viewModel: JobListViewModel = viewModel(factory = JobViewModelFactory.JobListFactory)
) {
    // Collect the StateFlow (live data stream) from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "JobConnect: Search") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddJobClick) {
                // FAB now uses the Bookmark icon and navigates to the Saved Jobs list
                Icon(Icons.Filled.BookmarkBorder, contentDescription = "View Saved Jobs")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        // Display loading state or the actual list
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.jobPosts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No search results available.", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            JobListContent(
                jobPosts = uiState.jobPosts,
                onJobClick = onJobClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Composable that renders the list content
@Composable
private fun JobListContent(
    jobPosts: List<JobPost>,
    onJobClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // LazyColumn is Compose's equivalent of a RecyclerView (efficient list rendering)
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        // FIX: Using explicit PaddingValues to solve the compiler reference issue
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // items() is a convenient way to iterate over a list in LazyColumn
        items(items = jobPosts, key = { it.id }) { jobPost ->
            JobPostCard(jobPost = jobPost, onJobClick = onJobClick)
        }
    }
}

// FIX: Removed 'private' keyword so this Card can be reused by SavedJobsScreen.kt
@Composable
fun JobPostCard(
    jobPost: JobPost,
    onJobClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onJobClick(jobPost.id) }, // Handles click to go to detail screen
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = jobPost.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = jobPost.company,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = jobPost.location, style = MaterialTheme.typography.bodyMedium)
                Text(text = jobPost.type, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
