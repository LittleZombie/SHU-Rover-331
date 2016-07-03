package com.littlezombie.shurover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import scoutsongs.littlezombie.com.scoutsongs.R;

/**
 * Created by Chia-En, Li on 2015/3/18.
 */
public class MusicAdapter extends BaseAdapter {

    private List<String> musicNameList;
    private LayoutInflater inflater;


    public MusicAdapter(Context context, List<String> musicNameList){
        this.musicNameList = musicNameList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return musicNameList.size();
    }

    @Override
    public String getItem(int position) {
        return musicNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.music_adapter_item, parent, false);

        TextView musicNameTextView = (TextView) convertView.findViewById(R.id.musicAdapterItem_textView);
        musicNameTextView.setText(musicNameList.get(position));

        return convertView;
    }

}
