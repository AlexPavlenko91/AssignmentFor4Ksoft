package com.alex.assignmentfor4ksoft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alex.assignmentfor4ksoft.authorization.presentation.AuthorizationScreen
import com.alex.assignmentfor4ksoft.feature_posts.presentation.PostsScreen
import com.alex.assignmentfor4ksoft.ui.theme.AssignmentFor4KsoftTheme
import com.alex.assignmentfor4ksoft.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentFor4KsoftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.AuthorizationScreen.route
                    ) {
                        composable(route = Screen.AuthorizationScreen.route) {
                            AuthorizationScreen(navController = navController)
                        }
                        composable(route = Screen.PostsScreen.route) {
                            PostsScreen()
                        }
                    }
                }
            }
        }
    }
}
