package com.littlezombie.shurover.view.compose.scout

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.littlezombie.shurover.view.compose.Screen
import com.littlezombie.shurover.view.compose.item.SongItem
import com.littlezombie.shurover.viewmodel.SongViewModel
import scoutsongs.littlezombie.com.scoutsongs.R

@Composable
fun ScoutSongScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SongViewModel
) {
    ScoutSongScreen(navController, viewModel)
}

@Composable
fun ScoutSongScreen(navController: NavController, viewModel: SongViewModel) {
    val context = LocalContext.current
    val scoutSongs =
        remember { context.resources.getStringArray(R.array.scout_songs_chinese_name).toList() }
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        itemsIndexed(scoutSongs) { index, songName ->
            val fileName = context.resources.getStringArray(R.array.scout_songs_english_name)[index]
            val lyrics = context.resources.getStringArray(R.array.scout_songs_lyrics)[index]
            SongItem(
                name = songName,
                onSongClicked = {
                    onSongItemClicked(context, viewModel, songName, fileName, lyrics)
                    navController.navigate(Screen.PlayMusic.route)
                }
            )
        }
    }
}

fun onSongItemClicked(
    context: Context,
    viewModel: SongViewModel,
    songName: String,
    fileName: String,
    lyrics: String
) {
    val resId = context.resources.getIdentifier(fileName, "raw", context.packageName)
    val mediaPlayer = MediaPlayer.create(context, resId)
    viewModel.setPlaySongData(mediaPlayer, songName, lyrics)
}