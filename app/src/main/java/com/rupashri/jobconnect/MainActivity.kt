package com.rupashri.jobconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rupashri.jobconnect.ui.JobConnectNavHost
import com.rupashri.jobconnect.ui.theme.JobConnectTheme // Assuming this is the correct path

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobConnectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Get NavController instance
                    val navController = rememberNavController()

                    // 2. Use the central NavHost
                    JobConnectNavHost(navController = navController)
                }
            }
        }
    }
}
