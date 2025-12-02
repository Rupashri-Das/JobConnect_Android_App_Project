package com.rupashri.jobconnect

import com.rupashri.jobconnect.data.JobPost
import com.rupashri.jobconnect.data.JobPostDao
import com.rupashri.jobconnect.repository.JobPostRepository
import com.rupashri.jobconnect.viewmodel.JobDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rupashri.jobconnect.data.FakeDataSource // To get a valid job post for testing

// Mockito creates a fake DAO that we can control
private lateinit var mockDao: JobPostDao

@OptIn(ExperimentalCoroutinesApi::class)
class JobDetailViewModelTest {

    // Executes tasks instantly in the main thread (needed for testing coroutines/viewmodels)
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Use a test dispatcher for coroutine control
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    // Get a valid test job from the fake data source
    private val testJob = FakeDataSource.jobList.first()

    @Before
    fun setup() {
        // Set up the Mockito mock
        mockDao = mock(JobPostDao::class.java)

        // Set the main dispatcher for coroutines to the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun jobDetailViewModel_initialization_setsLoadingToFalse() = runTest {
        // ARRANGE
        val repository = JobPostRepository(mockDao)

        // ACT: Initialize ViewModel
        val viewModel = JobDetailViewModel(repository, savedStateHandle = null)

        // Advance time to allow coroutines (init block) to run
        advanceUntilIdle()

        // ASSERT: Check that the UI state is not loading after initialization
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun jobDetailViewModel_toggleSaveStatus_updatesState() = runTest {
        // ARRANGE
        val repository = JobPostRepository(mockDao)

        // FIX: ViewModel instance must be declared as 'var' for modification in the test
        var viewModel = JobDetailViewModel(repository, savedStateHandle = null)

        // Manually set a known loaded state (job loaded, initially not saved)
        // We cast to MutableStateFlow to directly set the internal state for testing purposes
        (viewModel.uiState as MutableStateFlow).value = viewModel.uiState.value.copy(
            jobPost = testJob,
            isSaved = false,
            isLoading = false
        )

        // ACT 1: Toggle save status from false to true
        viewModel.toggleSaveStatus()
        advanceUntilIdle()

        // ASSERT 1: State should be saved
        assertEquals(true, viewModel.uiState.value.isSaved)

        // ACT 2: Toggle save status from true to false
        viewModel.toggleSaveStatus()
        advanceUntilIdle()

        // ASSERT 2: State should be unsaved
        assertEquals(false, viewModel.uiState.value.isSaved)
    }
}
