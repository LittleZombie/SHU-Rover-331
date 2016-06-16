package scoutsongs.littlezombie.com.scoutsongs.song;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import scoutsongs.littlezombie.com.scoutsongs.R;
import scoutsongs.littlezombie.com.scoutsongs.adapter.ViewPagerAdapter;


public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_activity);
        initToolbar();
        initTabLayout();
        initViewPager();
    }

    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mainActivity_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.scout_song)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.activity_song)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        toolbar.setLogo(ContextCompat.getDrawable(this, R.drawable.logo));
        toolbar.setTitle(getResources().getString(R.string.toolbar_title));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        setSupportActionBar(toolbar);
    }

    private void initViewPager() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mainActivity_tabLayout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.mainActivity_viewPager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
