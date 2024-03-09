package com.littlezombie.shurover.view.compose.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.littlezombie.shurover.view.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongItem(
    name: String,
    onSongClicked: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = { onSongClicked() }
    ) {
        Row(
            modifier = Modifier.background(color = White).fillMaxWidth()
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}