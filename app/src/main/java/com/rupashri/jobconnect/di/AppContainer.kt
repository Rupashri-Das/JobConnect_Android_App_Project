package com.rupashri.jobconnect.di

import android.content.Context
import com.rupashri.jobconnect.data.JobConnectDatabase
import com.rupashri.jobconnect.repository.JobPostRepository

/**
 * Interface that defines the necessary dependency providers for the application.
 */
interface AppContainer {
    val jobPostRepository: JobPostRepository
}

/**
 * Implementation of AppContainer that provides actual instances of the dependencies.
 * It initializes the database and repository.
 */
class AppDataContainer(private val context: Context) : AppContainer {

    // 1. Database Initialization
    private val database: JobConnectDatabase by lazy {
        JobConnectDatabase.getDatabase(context = context)
    }

    // 2. Repository Initialization (Uses the DAO from the database)
    override val jobPostRepository: JobPostRepository by lazy {
        JobPostRepository(database.jobPostDao())
    }
}
