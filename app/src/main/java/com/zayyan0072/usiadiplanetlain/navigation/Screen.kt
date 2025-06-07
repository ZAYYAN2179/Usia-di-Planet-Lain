package com.zayyan0072.usiadiplanetlain.navigation

import com.zayyan0072.usiadiplanetlain.ui.theme.screen.KEY_ID_MISSION

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Count: Screen("ageCountScreen")
    data object MissionList: Screen("missionListScreen")
    data object DetailMission : Screen("detailMissionScreen")
    data object FormEdit: Screen("detailMissionScreen/{$KEY_ID_MISSION}") {
        fun withId(id: Long) = "detailMissionScreen/$id"
    }
    data object RecycleBin: Screen("recycleBinScreen")
    data object PlanetToolsScreen: Screen("planetToolsScreen")
}