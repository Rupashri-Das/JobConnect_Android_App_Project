package com.rupashri.jobconnect.repository

import com.rupashri.jobconnect.data.FakeDataSource
import com.rupashri.jobconnect.data.JobPost
import com.rupashri.jobconnect.data.JobPostDao
import kotlinx.coroutines.flow.Flow

// The Repository class handles data logic from all sources.
class JobPostRepository(private val jobPostDao: JobPostDao) {

    // --- FAKE (SEARCH) DATA SOURCE OPERATIONS ---

    // 1. Read All Searchable Jobs (for the Job Search Screen)
    fun getAllSearchableJobs(): List<JobPost> {
        return FakeDataSource.jobList
    }

    // 2. Get a Single Job (From the fake source, needed for the Detail Screen)
    fun getJobById(jobId: Int): JobPost? {
        return FakeDataSource.jobList.find { it.id == jobId }
    }


    // --- ROOM (SAVED JOBS) OPERATIONS ---

    // 3. Read All Saved Jobs: Returns the Flow from the DAO (for the Saved Jobs Screen).
    fun getAllSavedJobPosts(): Flow<List<JobPost>> = jobPostDao.getAllSavedJobPosts()

    // 4. Check if a Job is Saved: Checks the local database.
    suspend fun isJobSaved(jobId: Int): Boolean {
        return jobPostDao.getSavedJobPostById(jobId) != null
    }

    // 5. Save Job (Add to Favorites)
    suspend fun saveJob(jobPost: JobPost) {
        jobPostDao.saveJob(jobPost)
    }

    // 6. Unsave Job (Remove from Favorites)
    suspend fun unsaveJob(jobPost: JobPost) {
        // To unsave, we need the actual JobPost entity.
        // We assume the JobPost passed here is the one from the search list.
        jobPostDao.unsaveJob(jobPost)
    }
}
