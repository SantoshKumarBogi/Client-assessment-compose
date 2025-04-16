package com.lbg.assessment.presentation.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lbg.assessment.ui.theme.MyApplicationTheme
import com.lbg.core.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavHost()
            }
        }
    }

    @Composable
    private fun AppNavHost() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = AppConstants.USER_SCREEN) {
            composable(AppConstants.USER_SCREEN) {
                UserScreen(navController)
            }
            composable(
                "${AppConstants.USER_DETAILS_SCREEN}/{user}"
            ) { backStackEntry ->
                val userJson = backStackEntry.arguments?.getString("user")
                UserDetailsScreen(userJson)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        UserScreen(rememberNavController())
    }
}