package com.rupashri.jobconnect.viewmodel

import androidx.lifecycle.ViewModel
import com.rupashri.jobconnect.data.JobPost
import com.rupashri.jobconnect.repository.JobPostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Defines the UI state for the Job Search Screen.
 */
data class JobSearchUiState(
    val jobPosts: List<JobPost> = emptyList(),
    val isLoading: Boolean = false // We assume instant loading with fake data
)

// Renamed the class conceptually, but keeping JobListViewModel to minimize file changes.
class JobListViewModel(private val repository: JobPostRepository) : ViewModel() {

    // State that holds the list of all searchable jobs (from FakeDataSource)
    private val _uiState = MutableStateFlow(JobSearchUiState())
    val uiState: StateFlow<JobSearchUiState> = _uiState.asStateFlow()

    init {
        loadSearchableJobs()
    }

    private fun loadSearchableJobs() {
        // Since this is fake data, we can call it directly without coroutines.
        val allJobs = repository.getAllSearchableJobs()
        _uiState.value = JobSearchUiState(jobPosts = allJobs, isLoading = false)
    }
}
