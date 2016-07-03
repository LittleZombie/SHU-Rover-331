package com.littlezombie.shurover;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.littlezombie.shurover.manager.FacebookManager;
import com.littlezombie.shurover.song.SongActivity;
import com.littlezombie.shurover.widget.ProfilePictureView;

import scoutsongs.littlezombie.com.scoutsongs.R;

import static android.support.design.widget.NavigationView.*;


public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener
        , View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        layoutContentView();
    }

    private void layoutContentView() {
        layoutToolbar();
        layoutNavigationView();
        layoutProfile();
        layoutLoginButton();
    }

    private void layoutToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainActivity_drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void layoutNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.mainActivity_navigationView);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void layoutProfile() {
        Profile profile = FacebookManager.getInstance().getProfile();
        if (profile == null) {
            return;
        }

        View view = ((NavigationView) findViewById(R.id.mainActivity_navigationView)).getHeaderView(0);
        ProfilePictureView profilePictureView = (ProfilePictureView) view.findViewById(R.id.navigationView_profilePictureView);
        profilePictureView.setProfileId(profile.getId());

        TextView profileTextView = (TextView) view.findViewById(R.id.navigationView_hiTextView);
        String text = getString(R.string.profile_hi_text, profile.getName());
        profileTextView.setText(text);
    }

    private void layoutLoginButton() {
        LoginButton loginButton = (LoginButton) findViewById(R.id.mainActivity_facebookLoginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                break;
            case R.id.menu_facebook:
                break;
            case R.id.menu_skill_songs:
                startSongActivity();
                break;
            case R.id.menu_skill_knots:
                break;
        }
        return true;
    }

    private void startSongActivity() {
        Intent intent = new Intent(this, SongActivity.class);
        startActivity(intent);
    }

    private void LogOutSuccess() {
        LoginManager.getInstance().logOut();
        startStartActivity();
        finish();
    }

    private void startStartActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainActivity_facebookLoginButton:
                LogOutSuccess();
                break;
        }
    }
}
