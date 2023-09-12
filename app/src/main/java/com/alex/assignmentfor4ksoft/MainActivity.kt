package com.alex.assignmentfor4ksoft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alex.assignmentfor4ksoft.authorization.presentation.AuthorizationScreen
import com.alex.assignmentfor4ksoft.core.domain.preferences.DefaultPreferences
import com.alex.assignmentfor4ksoft.feature_posts.presentation.add_edit_post.AddEditPostScreen
import com.alex.assignmentfor4ksoft.feature_posts.presentation.posts.PostsScreen
import com.alex.assignmentfor4ksoft.ui.theme.AssignmentFor4KsoftTheme
import com.alex.assignmentfor4ksoft.utils.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: DefaultPreferences

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
                    val isRememberedUserInfo = preferences.loadIsRememberedUser()
                    NavHost(
                        navController = navController,
                        startDestination = if (isRememberedUserInfo) Screen.PostsScreen.route else Screen.AuthorizationScreen.route
                    ) {
                        composable(route = Screen.AuthorizationScreen.route) {
                            AuthorizationScreen(navController = navController)
                        }
                        composable(route = Screen.PostsScreen.route) {
                            PostsScreen(
                                navigateToLogin = { navController.navigate(Screen.AuthorizationScreen.route) },
                                navigateToAddEditScreen = { navController.navigate(Screen.AddEditPostScreen.route) },
                            )
                        }
                        composable(
                            route = Screen.AddEditPostScreen.route +
                                    "?postId={postId}&postColor={postColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "postId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "postColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("postColor") ?: -1
                            AddEditPostScreen(
                                navController = navController,
                                postColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
