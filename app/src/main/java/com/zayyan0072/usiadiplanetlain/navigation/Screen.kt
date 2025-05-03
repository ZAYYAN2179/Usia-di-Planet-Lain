package com.zayyan0072.usiadiplanetlain.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Count: Screen("ageCountScreen")
    data object MissionList: Screen("missionListScreen")
//    data object MissionForm : Screen("missionFormScreen")
//    data object MissionDetail : Screen("missionDetailScreen")
}