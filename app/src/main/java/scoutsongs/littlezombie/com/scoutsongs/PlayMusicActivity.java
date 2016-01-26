package scoutsongs.littlezombie.com.scoutsongs;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chia-En, Li on 2015/3/18.
 */
public class PlayMusicActivity extends Activity implements View.OnClickListener {

    public static final String INPUT_BUNDLE_MUSIC_POSITION = "INPUT_BUNDLE_MUSIC_POSITION";
    public static final String INPUT_BUNDLE_MUSIC_ENGLISH_ID = "INPUT_BUNDLE_MUSIC_ENGLISH_ID";
    public static final String INPUT_BUNDLE_MUSIC_CHINESE_ID = "INPUT_BUNDLE_MUSIC_CHINESE_ID";
    public static final String INPUT_BUNDLE_MUSIC_LYRICS_ID = "INPUT_BUNDLE_MUSIC_LYRICS_ID";

    private int musicPosition;
    private int englishNameListId;
    private int chineseNameListId;
    private int lyricsId;

    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        setContentView(R.layout.play_music_activity);

        getExtras();
        initMusic();

        layoutContentView();

        if (!isMusicNull()) {
            layoutButton();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        musicPosition = bundle.getInt(INPUT_BUNDLE_MUSIC_POSITION);
        englishNameListId = bundle.getInt(INPUT_BUNDLE_MUSIC_ENGLISH_ID);
        chineseNameListId = bundle.getInt(INPUT_BUNDLE_MUSIC_CHINESE_ID);
        lyricsId = bundle.getInt(INPUT_BUNDLE_MUSIC_LYRICS_ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Music.getInstance().pause();
    }

    @Override
    protected void onDestroy() {
        Music.getInstance().release();
        super.onDestroy();
    }

    private void initMusic() {
        String[] musicArray = getResources().getStringArray(englishNameListId);
        String musicName = musicArray[musicPosition];
        int musicId = getResources().getIdentifier(musicName, "raw", this.getPackageName());
        Music.getInstance().setPlayer(this, musicId);
    }

    private void layoutContentView() {
        layoutMusicName();
        layoutMusicLyrics();
        layoutSeekBar();
    }

    private void layoutSeekBar() {
        seekBar = (SeekBar) findViewById(R.id.playMusicActivity_seekBar);
        seekBar.setMax(Music.getInstance().getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Music.getInstance().seekTo(seekBar.getProgress());
            }
        });

    }

    private void layoutMusicName() {
        TextView musicNameTextView = (TextView) findViewById(R.id.playMusicActivity_musicNameTextView);
        musicNameTextView.setText(getTextViewShowString());
    }

    private void layoutMusicLyrics() {
        String[] musicLyricsArray = getResources().getStringArray(lyricsId);
        String musicLyrics = musicLyricsArray[musicPosition];

        if (isEmpty(musicLyrics)) {
            return;
        }

        TextView lyricsTextView = (TextView) findViewById(R.id.playMusicActivity_lyricsTextView);
        lyricsTextView.setText(musicLyrics);
    }

    private String getTextViewShowString() {
        if (isMusicNull()) {
            return "Sorry! The music has something error.";
        }
        return getMusicNameList().get(musicPosition);
    }

    private boolean isMusicNull() {
        return Music.getInstance().getPlayer() == null;
    }

    private List<String> getMusicNameList() {
        String[] musicArray = getResources().getStringArray(chineseNameListId);
        return new ArrayList<>(Arrays.asList(musicArray));
    }

    private void layoutButton() {
        ImageButton startButton = (ImageButton) findViewById(R.id.playMusicActivity_startButton);
        ImageButton pauseButton = (ImageButton) findViewById(R.id.playMusicActivity_pauseButton);
        ImageButton stopButton = (ImageButton) findViewById(R.id.playMusicActivity_stopButton);

        startButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playMusicActivity_startButton:
                Music.getInstance().start();
                UpdateSeekBar updateSeekBar = new UpdateSeekBar();
                updateSeekBar.execute(Music.getInstance().getCurrentPosition());
                break;
            case R.id.playMusicActivity_pauseButton:
                Music.getInstance().pause();
                break;
            case R.id.playMusicActivity_stopButton:
                Music.getInstance().stop();
                seekBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(0);
                    }
                }, 50);
                break;
        }
    }

    public boolean isEmpty(String string) {
        return string == null || string.equals("") || string.length() == 0;
    }


    private class UpdateSeekBar extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            PlayMusicActivity.this.seekBar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(Integer... params) {
            while (Music.getInstance().isRunning()) {
                publishProgress(Music.getInstance().getCurrentPosition());
            }
            return null;
        }

    }
}
