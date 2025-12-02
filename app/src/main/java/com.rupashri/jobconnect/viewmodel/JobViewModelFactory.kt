package com.rupashri.jobconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rupashri.jobconnect.JobConnectApplication

/**
 * Factory class to instantiate the ViewModels with the required dependencies.
 */
object JobViewModelFactory {

    // 1. Factory for JobListViewModel (Existing)
    val JobListFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JobConnectApplication
            val repository = application.container.jobPostRepository
            JobListViewModel(repository)
        }
    }

    // 2. Factory for JobDetailViewModel (FIXED: Bypassing problematic createFor)
    val JobDetailFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JobConnectApplication
            val repository = application.container.jobPostRepository
            // FIX: Use the simple 'createSavedStateHandle()' extension function
            // which resolves the navigation arguments without the complex 'createFor'.
            val savedStateHandle = this.createSavedStateHandle()

            JobDetailViewModel(repository, savedStateHandle)
        }
    }

    // 3. Factory for SavedJobsViewModel (Existing)
    val SavedJobsFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JobConnectApplication
            val repository = application.container.jobPostRepository

            SavedJobsViewModel(repository)
        }
    }
}
