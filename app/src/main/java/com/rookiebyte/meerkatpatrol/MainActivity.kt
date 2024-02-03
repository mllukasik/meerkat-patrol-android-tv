@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.rookiebyte.meerkatpatrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.rookiebyte.meerkatpatrol.ui.theme.MeerkatPatrolTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeerkatPatrolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Camera.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Camera.route) {
            CameraView(navController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsView(navController)
        }
    }
}

sealed class NavigationItem(val route: String) {
    data object Camera : NavigationItem("camera")
    data object Settings : NavigationItem("settings")
}
