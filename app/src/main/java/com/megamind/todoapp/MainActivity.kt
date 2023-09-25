package com.megamind.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.megamind.todoapp.ui.ToDoAppScreen
import com.megamind.todoapp.ui.screens.AboutScreen
import com.megamind.todoapp.ui.screens.DetailScreen
import com.megamind.todoapp.ui.screens.LoginScreen
import com.megamind.todoapp.ui.screens.SettingsScreen
import com.megamind.todoapp.ui.screens.SignInScreen
import com.megamind.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                ToDoApp()
            }
        }
    }
}


@Composable
fun ToDoApp(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = ToDoAppScreen.splashScreen.name,

        ) {

        composable(ToDoAppScreen.splashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(ToDoAppScreen.todoList.name) {
            ToDoListScreen(
                onItemClicked = {
                    navController.navigate(ToDoAppScreen.detailScreen.name + "/$it")
                },

                onAboutMenu = {
                    navController.navigate(ToDoAppScreen.signInScreen.name)
                },

                onSettingsMenu = {

                    navController.navigate(ToDoAppScreen.settingsScreen.name)
                }


            )

        }

        composable(ToDoAppScreen.settingsScreen.name) {
            SettingsScreen(onTextClicked = { navController.popBackStack() })
        }

        composable(route = ToDoAppScreen.aboutScreen.name) {
            AboutScreen()
        }

        composable(
            ToDoAppScreen.detailScreen.name + "/{taskId}",
            arguments = listOf(navArgument("taskId") {
                NavType.StringType
            })
        ) {

            val params = it.arguments?.getString("taskId")
            if (params != null) {
                DetailScreen(navController = navController, id = params)
            }
        }

        composable(ToDoAppScreen.signInScreen.name) {
            SignInScreen(navController = navController)
        }

        composable(ToDoAppScreen.loginScreen.name) {
            LoginScreen(navController = navController)
        }


    }
}