package com.rupashri.jobconnect.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// This annotation tells Room to treat this class as a database table.
// The tableName argument specifies the name of the table in the database.
@Entity(tableName = "job_posts")
data class JobPost(
    // PrimaryKey annotation marks this field as the unique identifier for the table.
    // autoGenerate = true tells Room to automatically assign an incrementing ID for new entries.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String, // e.g., "Senior Android Developer"
    val company: String, // e.g., "Tech Innovations Inc."
    val description: String, // Full job details
    val location: String, // e.g., "Remote", "Kuopio, Finland"
    val salaryRange: String, // e.g., "€50k - €70k"
    val type: String // e.g., "Full-time", "Part-time", "Contract"
)
