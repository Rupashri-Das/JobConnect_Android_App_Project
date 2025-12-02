package com.rupashri.jobconnect.data

// This object holds hardcoded data to simulate a remote job API feed.
object FakeDataSource {
    // We use distinct IDs (starting at 101) for the searchable jobs
    // to distinguish them from Room's auto-generated IDs (which are small)
    // when a job is saved locally.
    val jobList = listOf(
        JobPost(
            id = 101,
            title = "Junior Kotlin Developer",
            company = "Tech Startup Oy",
            description = "Exciting opportunity for a recent graduate in Tampere. Focus on Android Compose development. Must know coroutines. We are building the next generation of financial apps.",
            location = "Tampere, Finland",
            salaryRange = "€35k - €45k",
            type = "Full-time"
        ),
        JobPost(
            id = 102,
            title = "UI/UX Designer Internship",
            company = "Design Connect Inc.",
            description = "Six-month paid internship focusing on Material 3 guidelines and accessibility standards. Remote flexibility (EU time zone). Portfolio required.",
            location = "Remote",
            salaryRange = "€1,500/month stipend",
            type = "Internship"
        ),
        JobPost(
            id = 103,
            title = "Data Analyst (Entry Level)",
            company = "Global Insights Group",
            description = "Analyze market data and generate reports. Strong Excel and SQL skills are a plus. Located near Kuopio University. Excellent growth opportunities.",
            location = "Kuopio, Finland",
            salaryRange = "€40k - €55k",
            type = "Full-time"
        ),
        JobPost(
            id = 104,
            title = "Part-time IT Support",
            company = "Campus Solutions",
            description = "Flexible hours to fit a student schedule. Provide technical support to university staff and students on campus hardware and software.",
            location = "Oulu, Finland",
            salaryRange = "€18/hour",
            type = "Part-time"
        ),
        JobPost(
            id = 105,
            title = "Marketing Assistant Internship",
            company = "Future Growth Agency",
            description = "Assist the marketing team with social media content and campaign tracking. Basic knowledge of digital marketing tools is a bonus.",
            location = "Helsinki, Finland",
            salaryRange = "€1,200/month stipend",
            type = "Internship"
        )
    )
}
