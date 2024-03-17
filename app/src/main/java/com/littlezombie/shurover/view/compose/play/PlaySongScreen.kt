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
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.littlezombie.shurover.view.compose.home.HomeTopAppBar
import com.littlezombie.shurover.view.ui.theme.Green
import com.littlezombie.shurover.viewmodel.SongViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import scoutsongs.littlezombie.com.scoutsongs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySongScreen(
    viewModel: SongViewModel,
    onKeepScreenOn: () -> Unit,
    clearKeepScreenOn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val duration = remember { viewModel.getDuration() }
    val scope = rememberCoroutineScope()
    var currentPosition by remember { mutableStateOf(viewModel.getCurrentPosition()) }
    val songName = viewModel.songName.orEmpty()
    val lyrics = viewModel.lyrics.orEmpty()

    LaunchedEffect(key1 = currentPosition) {
        currentPosition = viewModel.getCurrentPosition()
    }

    Scaffold(
        topBar = { HomeTopAppBar(modifier = modifier) },
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mao2_back),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
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
                            viewModel.seekTo(currentPosition)
                        },
                        valueRange = 0f..duration.toFloat(),
                        colors = SliderDefaults.colors(
                            activeTrackColor = Green,
                            inactiveTickColor = Color.White
                        )
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
                                viewModel.startPlaying()
                                scope.launch {
                                    while (viewModel.isRunning()) {
                                        currentPosition = viewModel.getCurrentPosition()
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
                                viewModel.pausePlaying()
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
                                viewModel.stop()
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
            viewModel.release()
            scope.cancel()
        }
    }
}