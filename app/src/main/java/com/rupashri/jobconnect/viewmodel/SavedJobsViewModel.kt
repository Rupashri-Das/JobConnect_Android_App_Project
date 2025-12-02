package com.rupashri.jobconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rupashri.jobconnect.data.JobPost
import com.rupashri.jobconnect.repository.JobPostRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Defines the UI state for the Saved Jobs Screen.
 */
data class SavedJobsUiState(
    val savedJobPosts: List<JobPost> = emptyList(),
    val isLoading: Boolean = true
)

class SavedJobsViewModel(private val repository: JobPostRepository) : ViewModel() {

    // The StateFlow collects the live list of saved jobs from the Room database.
    // It updates automatically whenever a job is saved or unsaved on the Detail Screen.
    val uiState: StateFlow<SavedJobsUiState> =
        repository.getAllSavedJobPosts()
            .map { jobList ->
                SavedJobsUiState(
                    savedJobPosts = jobList,
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SavedJobsUiState()
            )
}
