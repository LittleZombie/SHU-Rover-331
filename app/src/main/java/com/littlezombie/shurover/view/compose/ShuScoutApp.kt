package com.littlezombie.shurover.view.compose

import android.app.Activity
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.littlezombie.shurover.view.compose.home.HomeScreen
import com.littlezombie.shurover.view.compose.play.PlaySongScreen
import com.littlezombie.shurover.viewmodel.SongViewModel

@Composable
fun ShuScoutApp(viewModel: SongViewModel) {
    val navController = rememberNavController()
    ShuScoutNavHost(navController, viewModel)
}

@Composable
fun ShuScoutNavHost(
    navController: NavHostController,
    viewModel: SongViewModel
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.PlayMusic.route) {
            PlaySongScreen(
                onKeepScreenOn = {
                    (context as? Activity)?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                },
                clearKeepScreenOn = {
                    (context as? Activity)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                },
                viewModel = viewModel
            )
        }
    }
}