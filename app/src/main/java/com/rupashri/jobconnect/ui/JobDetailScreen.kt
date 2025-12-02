package com.rupashri.jobconnect.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rupashri.jobconnect.data.JobPost
import com.rupashri.jobconnect.viewmodel.JobDetailViewModel
import com.rupashri.jobconnect.viewmodel.JobViewModelFactory

/**
 * Composable function for the Job Detail Screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    // Inject ViewModel using the Factory we created
    viewModel: JobDetailViewModel = viewModel(factory = JobViewModelFactory.JobDetailFactory)
) {
    val uiState by viewModel.uiState.collectAsState()
    // FIX: Read the state once into an immutable local variable to avoid smart cast error
    val currentJobPost = uiState.jobPost

    Scaffold(
        topBar = {
            JobDetailTopBar(
                title = currentJobPost?.title ?: "Job Details",
                isSaved = uiState.isSaved,
                onNavigateBack = onNavigateBack,
                onToggleSave = viewModel::toggleSaveStatus,
                canToggleSave = currentJobPost != null && !uiState.isLoading
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
                // Check if the stable local variable is null
                currentJobPost == null -> Text("Job not found.", color = MaterialTheme.colorScheme.error)
                // If not null, render the content using the stable local variable
                else -> JobDetailContent(jobPost = currentJobPost)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobDetailTopBar(
    title: String,
    isSaved: Boolean,
    onNavigateBack: () -> Unit,
    onToggleSave: () -> Unit,
    canToggleSave: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (canToggleSave) {
                IconButton(onClick = onToggleSave) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = if (isSaved) "Unsave Job" else "Save Job",
                        tint = if (isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
private fun JobDetailContent(jobPost: JobPost) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Allows content to scroll if too long
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Title and Company ---
        Text(
            text = jobPost.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = jobPost.company,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Divider()

        // --- Key Details Row ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailItem(label = "Location", value = jobPost.location, modifier = Modifier.weight(1f))
            DetailItem(label = "Type", value = jobPost.type, modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        DetailItem(label = "Salary Range", value = jobPost.salaryRange, isFullWidth = true)

        Divider()

        // --- Description ---
        Text(
            text = "Job Description",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = jobPost.description,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isFullWidth: Boolean = false
) {
    Column(
        modifier = modifier.then(if (isFullWidth) Modifier.fillMaxWidth() else Modifier.padding(end = 8.dp))
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
