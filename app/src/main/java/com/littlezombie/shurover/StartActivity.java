package com.littlezombie.shurover;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.littlezombie.shurover.manager.FacebookManager;

import scoutsongs.littlezombie.com.scoutsongs.R;

/**
 * Created by user on 2016/7/3.
 */
public class StartActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private String TAG = getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initFacebook();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        layoutLoginButton();
        judgeUserIsLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCallBackManager().onActivityResult(requestCode, resultCode, data);
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().registerCallback(getCallBackManager(), this);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        onUserIsLogin();
    }

    @Override
    public void onCancel() {
        showToast("Cancel");
    }

    @Override
    public void onError(FacebookException error) {
        showToast("Error : " + error.getMessage());
    }

    private void layoutLoginButton() {
        LoginButton loginButton = (LoginButton) findViewById(R.id.startActivity_facebookLoginButton);
        loginButton.setVisibility(isUserLogin() ? View.GONE : View.VISIBLE);
        loginButton.registerCallback(getCallBackManager(), this);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void judgeUserIsLogin(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isUserLogin()){
                    onUserIsLogin();
                }
            }
        }, 800);
    }

    private void onUserIsLogin() {
        startMainActivity();
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private CallbackManager getCallBackManager(){
        return FacebookManager.getInstance().getCallbackManager();
    }

    private boolean isUserLogin(){
        return FacebookManager.getInstance().isLogin();
    }
}
