package com.littlezombie.shurover.view.compose

import android.app.Activity
import android.media.MediaPlayer
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            HomeScreen(navController = navController)
        }
        composable(
            route = "${Screen.PlayMusic.route}/{songName}/{fileName}/{lyrics}",
            arguments = listOf(
                navArgument("songName") { type = NavType.StringType },
                navArgument("fileName") { type = NavType.StringType },
                navArgument("lyrics") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val songName = backStackEntry.arguments?.getString("songName").orEmpty()
            val fileName = backStackEntry.arguments?.getString("fileName").orEmpty()
            val lyrics = backStackEntry.arguments?.getString("lyrics").orEmpty()

            val resId = context.resources.getIdentifier(fileName, "raw", context.packageName)
            val mediaPlayer = MediaPlayer.create(context, resId)
            viewModel.setMediaPlayer(mediaPlayer)

            PlaySongScreen(
                navController = navController,
                songName = songName,
                lyrics = lyrics,
                onKeepScreenOn = {
                    (context as? Activity)?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                },
                clearKeepScreenOn = {
                    (context as? Activity)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                },
                songViewModel = viewModel
            )
        }
    }
}