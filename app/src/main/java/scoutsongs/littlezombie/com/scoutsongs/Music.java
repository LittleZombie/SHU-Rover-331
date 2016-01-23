package scoutsongs.littlezombie.com.scoutsongs;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Chia-En, Li on 2015/3/18.
 */
public class Music {

    private static Music instance;

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }



    private MediaPlayer player;

    public void setPlayer(Context context, int musicId) {
        player = MediaPlayer.create(context, musicId);
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void prepare() throws IOException {
        player.prepare();
        player.seekTo(0);
    }

    public void pause(){
        if(player == null || !isRunning()){
            return ;
        }
        player.pause();
    }

    public void start(){
        player.start();
    }

    public void stop(){
        player.stop();
        try {
            prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning(){
        return player.isPlaying();
    }

    public int getCurrentPosition(){
        return player.getCurrentPosition();
    }

    public int getDuration(){
        return player.getDuration();
    }

    public void seekTo(int msec){
        player.seekTo(msec);
    }

    public void release(){
        player.release();
    }
}
