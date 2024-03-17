package com.littlezombie.shurover.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import java.io.IOException

class SongViewModel : ViewModel() {

    private var mediaPlayer: MediaPlayer? = null
    var songName: String? = null
        private set
    var lyrics: String? = null
        private set

    fun setPlaySongData(
        mediaPlayer: MediaPlayer,
        songName: String,
        lyrics: String
    ) {
        this.mediaPlayer = mediaPlayer
        this.songName = songName
        this.lyrics = lyrics
    }

    fun prepare() {
        mediaPlayer?.prepare()
        mediaPlayer?.seekTo(0)
    }

    fun startPlaying() {
        mediaPlayer?.start()
    }

    fun pausePlaying() {
        if (isRunning()) {
            mediaPlayer?.pause()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        try {
            prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun isRunning(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun getCurrentPosition(): Float {
        return mediaPlayer?.currentPosition?.toFloat() ?: 0f
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }

    fun seekTo(currentPosition: Float) {
        mediaPlayer?.seekTo(currentPosition.toInt())
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        songName = null
        lyrics = null
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
    }
}