package com.rupashri.jobconnect.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// The @Dao annotation marks this interface as a Room Data Access Object.
@Dao
interface JobPostDao {

    // QUERY: Retrieve all SAVED job posts, ordered by ID descending.
    @Query("SELECT * FROM job_posts ORDER BY id DESC")
    fun getAllSavedJobPosts(): Flow<List<JobPost>>

    // QUERY: Check if a job is saved (used for detail screen)
    @Query("SELECT * FROM job_posts WHERE id = :jobId")
    suspend fun getSavedJobPostById(jobId: Int): JobPost?

    // INSERT: Save (Favorite) a new job post.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveJob(jobPost: JobPost)

    // DELETE: Remove (Unfavorite) a job post.
    @Delete
    suspend fun unsaveJob(jobPost: JobPost)
}
