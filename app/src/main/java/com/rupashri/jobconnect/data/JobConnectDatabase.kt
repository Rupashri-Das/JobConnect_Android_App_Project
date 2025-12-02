package com.rupashri.jobconnect.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Database Annotation:
@Database(
    entities = [JobPost::class],
    version = 1,
    exportSchema = false
)
// Make sure you use the 'abstract' keyword right here:
abstract class JobConnectDatabase : RoomDatabase() {

    // 2. DAO Abstract Function:
    abstract fun jobPostDao(): JobPostDao

    // 3. Companion Object (Singleton Pattern):
    companion object {
        @Volatile
        private var Instance: JobConnectDatabase? = null

        fun getDatabase(context: Context): JobConnectDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    JobConnectDatabase::class.java,
                    "jobconnect_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
