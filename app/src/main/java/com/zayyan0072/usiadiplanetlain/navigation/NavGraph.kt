package com.zayyan0072.usiadiplanetlain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zayyan0072.usiadiplanetlain.ui.theme.screen.AboutScreen
import com.zayyan0072.usiadiplanetlain.ui.theme.screen.AgeCountScreen
import com.zayyan0072.usiadiplanetlain.ui.theme.screen.DetailMission
import com.zayyan0072.usiadiplanetlain.ui.theme.screen.KEY_ID_MISSION
import com.zayyan0072.usiadiplanetlain.ui.theme.screen.MainScreen
import com.zayyan0072.usiadiplanetlain.ui.theme.screen.MissionListScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Count.route) {
            AgeCountScreen(navController)
        }
        composable(route = Screen.MissionList.route) {
            MissionListScreen(navController)
        }
        composable(route = Screen.DetailMission.route) {
            DetailMission(navController)
        }
        composable(
            route = Screen.FormEdit.route,
            arguments = listOf(
                navArgument(KEY_ID_MISSION) {type = NavType.LongType}
            )
        ) {
            navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_MISSION)
            DetailMission(navController, id)
        }
    }
}