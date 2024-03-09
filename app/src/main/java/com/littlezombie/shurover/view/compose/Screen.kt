package com.littlezombie.shurover.view.compose

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    object Home : Screen("home")
    object PlayMusic : Screen("playMusic")
}
