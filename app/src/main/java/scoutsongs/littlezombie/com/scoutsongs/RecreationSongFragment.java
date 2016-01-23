package scoutsongs.littlezombie.com.scoutsongs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import scoutsongs.littlezombie.com.scoutsongs.adapter.MusicAdapter;

/**
 * Created by Chia-En, Li on 2015/3/30.
 */
public class RecreationSongFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scout_song_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutMusicList();
    }

    private void layoutMusicList() {
        MusicAdapter musicAdapter = new MusicAdapter(getActivity(), getMusicNameList());

        if (getView() == null) {
            return;
        }

        ListView musicNameListView = (ListView) getView().findViewById(R.id.scoutSongFragment_listView);
        if (musicNameListView == null) {
            return;
        }

        musicNameListView.setAdapter(musicAdapter);
        musicNameListView.setOnItemClickListener(this);

    }

    private List<String> getMusicNameList() {
        String[] musicArray = getResources().getStringArray(R.array.recreation_songs_chinese_name);
        return new ArrayList<>(Arrays.asList(musicArray));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startPlayMusicActivity(position);
    }

    private void startPlayMusicActivity(int musicPosition) {
        Intent intent = new Intent();

        Bundle bundle = new Bundle();
        bundle.putInt(PlayMusicActivity.INPUT_BUNDLE_MUSIC_POSITION, musicPosition);
        bundle.putInt(PlayMusicActivity.INPUT_BUNDLE_MUSIC_ENGLISH_ID, R.array.recreation_songs_english_name);
        bundle.putInt(PlayMusicActivity.INPUT_BUNDLE_MUSIC_CHINESE_ID, R.array.recreation_songs_chinese_name);
        bundle.putInt(PlayMusicActivity.INPUT_BUNDLE_MUSIC_LYRICS_ID, R.array.recreation_songs_lyrics);

        intent.putExtras(bundle);
        intent.setClass(getActivity(), PlayMusicActivity.class);

        startActivity(intent);
    }
}
