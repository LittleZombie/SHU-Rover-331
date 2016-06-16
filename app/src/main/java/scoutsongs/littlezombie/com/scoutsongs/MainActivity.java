package scoutsongs.littlezombie.com.scoutsongs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import scoutsongs.littlezombie.com.scoutsongs.song.SongActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        layoutContentView();
    }

    private void layoutContentView() {
        layoutToolbar();

        NavigationView navigationView = (NavigationView) findViewById(R.id.mainActivity_navigationView);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void layoutToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainActivity_drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer
                , toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                break;
            case R.id.menu_song:
                startSongActivity();
                break;
            case R.id.menu_knots:
                break;
        }
        return false;
    }

    private void startSongActivity() {
        Intent intent = new Intent(this, SongActivity.class);
        startActivity(intent);
    }
}
