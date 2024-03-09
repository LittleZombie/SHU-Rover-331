package com.littlezombie.shurover.view.compose.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.littlezombie.shurover.view.ui.theme.Green
import com.littlezombie.shurover.view.ui.theme.White
import com.littlezombie.shurover.viewmodel.SongViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import scoutsongs.littlezombie.com.scoutsongs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySongScreen(
    navController: NavController,
    songName: String,
    lyrics: String,
    songViewModel: SongViewModel,
    onKeepScreenOn: () -> Unit,
    clearKeepScreenOn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val duration = remember { songViewModel.getDuration() }
    val scope = rememberCoroutineScope()
    var currentPosition by remember { mutableStateOf(songViewModel.getCurrentPosition()) }

    LaunchedEffect(key1 = currentPosition) {
        currentPosition = songViewModel.getCurrentPosition()
    }

    Scaffold(
        topBar = { PlaySongTopAppBar(modifier, navController, songName) },
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                // 在此放置您的內容
                Image(
                    painter = painterResource(id = R.drawable.mao2_back),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = songName, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = lyrics, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(32.dp))
                    // 顯示 SeekBar
                    Slider(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        value = currentPosition,
                        onValueChange = {
                            currentPosition = it
                            songViewModel.seekTo(currentPosition)
                        },
                        valueRange = 0f..duration.toFloat(),
                        steps = 100
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            modifier = Modifier.size(88.dp),
                            onClick = {
                                onKeepScreenOn.invoke()
                                songViewModel.startPlaying()
                                scope.launch {
                                    while (songViewModel.isRunning()) {
                                        currentPosition = songViewModel.getCurrentPosition()
                                        delay(1000)
                                    }
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.play_btn),
                                contentDescription = "Play"
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        IconButton(
                            modifier = Modifier.size(88.dp),
                            onClick = {
                                songViewModel.pausePlaying()
                                clearKeepScreenOn.invoke()
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pause_btn),
                                contentDescription = "Pause"
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        IconButton(
                            modifier = Modifier.size(88.dp),
                            onClick = {
                                songViewModel.stop()
                                currentPosition = 0f
                                clearKeepScreenOn.invoke()
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.stop_btn),
                                contentDescription = "Stop"
                            )
                        }
                    }
                }
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            songViewModel.release()
            scope.cancel()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySongTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    songName: String
) {
//    TopAppBar(
//        modifier = Modifier.background(Dark_Green),
//        title = {
//            Text(text = songName)
//        },
//        navigationIcon = {
//            IconButton(onClick = { navController.popBackStack() }) {
//                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//            }
//        }
//    )

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Green,
            titleContentColor = White
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = songName,
                    Modifier.padding(start = 8.dp)
                )
            }
        }
    )
}