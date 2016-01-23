package scoutsongs.littlezombie.com.scoutsongs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import scoutsongs.littlezombie.com.scoutsongs.RecreationSongFragment;
import scoutsongs.littlezombie.com.scoutsongs.ScoutSongFragment;

/**
 * Created by Chia-en Li on 2015/12/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final static int PAGE_NUM = 2;
    private Fragment scoutSongFragment;
    private Fragment recreationSongFragment;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return createScoutSongFragment();
            case 1:
            default:
                return createRecreationSongFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    private Fragment createScoutSongFragment() {
        if (scoutSongFragment == null) {
            scoutSongFragment = new ScoutSongFragment();
        }
        return scoutSongFragment;
    }

    private Fragment createRecreationSongFragment() {
        if (recreationSongFragment == null) {
            recreationSongFragment = new RecreationSongFragment();
        }
        return recreationSongFragment;
    }
}
