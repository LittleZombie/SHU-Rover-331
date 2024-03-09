package com.littlezombie.shurover.view.compose.group

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
import scoutsongs.littlezombie.com.scoutsongs.R

@Composable
fun GroupSongScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    GroupSongScreen(navController)
}

@Composable
fun GroupSongScreen(navController: NavController) {
    val context = LocalContext.current
    val scoutSongs =
        remember { context.resources.getStringArray(R.array.group_songs_chinese_name).toList() }
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        itemsIndexed(scoutSongs) { index, songName ->
            val fileName = context.resources.getStringArray(R.array.group_songs_english_name)[index]
            val lyrics = context.resources.getStringArray(R.array.group_songs_lyrics)[index]
            SongItem(
                name = songName,
                onSongClicked = {
                    navController.navigate("${Screen.PlayMusic.route}/$songName/$fileName/$lyrics")
                }
            )
        }
    }
}