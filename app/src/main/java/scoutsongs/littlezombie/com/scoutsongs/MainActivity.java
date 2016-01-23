package scoutsongs.littlezombie.com.scoutsongs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import scoutsongs.littlezombie.com.scoutsongs.adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
