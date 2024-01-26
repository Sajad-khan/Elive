package com.tropat.elive.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tropat.elive.screens.SplashScreenContent
import com.tropat.elive.screens.details.DetailScreenContent
import com.tropat.elive.screens.home.HomeScreenContent
import com.tropat.elive.screens.login.LoginScreenContent
import com.tropat.elive.screens.search.BookSearchViewModel
import com.tropat.elive.screens.search.SearchScreenContent
import com.tropat.elive.screens.signup.SignUpScreenContent
import com.tropat.elive.screens.stats.StatsScreenContent
import com.tropat.elive.screens.update.UpdateScreenContent

@Composable
fun EliveNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = EliveScreens.SplashScreen.name,
        builder = {
            composable(EliveScreens.SplashScreen.name){
                SplashScreenContent(navController = navController)
            }
            composable(EliveScreens.HomeScreen.name){
                HomeScreenContent(navController = navController)
            }
            composable(EliveScreens.DetailScreen.name){
                DetailScreenContent(navController = navController)
            }
            composable(EliveScreens.LoginScreen.name){
                LoginScreenContent(navController = navController)
            }
            composable(EliveScreens.SearchScreen.name){
                val searchViewModel = hiltViewModel<BookSearchViewModel>()
                SearchScreenContent(navController = navController, searchViewModel)
            }
            composable(EliveScreens.UpdateScreen.name){
                UpdateScreenContent(navController = navController)
            }
            composable(EliveScreens.StatsScreen.name){
                StatsScreenContent(navController = navController)
            }
            composable(EliveScreens.SignUpScreen.name){
                SignUpScreenContent(navController = navController)
            }
        })
}