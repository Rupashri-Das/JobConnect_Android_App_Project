package com.rupashri.jobconnect.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rupashri.jobconnect.data.JobPost
import com.rupashri.jobconnect.repository.JobPostRepository
import com.rupashri.jobconnect.ui.JobDetailDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Defines the UI state for the Job Detail Screen.
 */
data class JobDetailUiState(
    val jobPost: JobPost? = null,
    val isSaved: Boolean = false,
    val isLoading: Boolean = true
)

class JobDetailViewModel(
    private val repository: JobPostRepository,
    // The SavedStateHandle is optional here. It will be passed by the factory.
    savedStateHandle: SavedStateHandle? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(JobDetailUiState())
    val uiState: StateFlow<JobDetailUiState> = _uiState.asStateFlow()

    // Extract the job ID from the navigation arguments using the nullable handle
    private val jobId: Int = savedStateHandle?.get<Int>(JobDetailDestination.jobIdArg) ?: 0

    init {
        loadJobDetails()
    }

    private fun loadJobDetails() {
        viewModelScope.launch {
            // If jobId is 0 (missing argument), we can't load anything.
            if (jobId == 0) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            // 1. Get the Job Post from the Fake Search Data
            val jobPost = repository.getJobById(jobId)

            if (jobPost != null) {
                // 2. Check the saved status from the Room database
                val isJobSaved = repository.isJobSaved(jobId)

                _uiState.update { currentState ->
                    currentState.copy(
                        jobPost = jobPost,
                        isSaved = isJobSaved,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun toggleSaveStatus() {
        viewModelScope.launch {
            val currentJob = _uiState.value.jobPost ?: return@launch
            val wasSaved = _uiState.value.isSaved

            if (wasSaved) {
                repository.unsaveJob(currentJob)
            } else {
                repository.saveJob(currentJob)
            }

            _uiState.update { it.copy(isSaved = !wasSaved) }
        }
    }

    fun jobPostExists(): Boolean = _uiState.value.jobPost != null
}
