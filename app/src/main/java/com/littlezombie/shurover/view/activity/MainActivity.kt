package com.littlezombie.shurover.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.littlezombie.shurover.view.compose.ShuScoutApp
import com.littlezombie.shurover.view.ui.theme.AppTheme
import com.littlezombie.shurover.viewmodel.SongViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SongViewModel::class.java]
        setContent {
            AppTheme {
                ShuScoutApp(viewModel)
            }
        }
    }
}