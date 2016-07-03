package com.littlezombie.shurover.manager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.Profile;

/**
 * Created by user on 2016/7/3.
 */
public class FacebookManager {

    private static FacebookManager facebookManager;
    private static CallbackManager callbackManager;

    public FacebookManager() {
        callbackManager = CallbackManager.Factory.create();
    }

    public synchronized static FacebookManager getInstance() {
        if (facebookManager == null) {
            facebookManager = new FacebookManager();
        }
        return facebookManager;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public boolean isLogin() {
        return getAccessToken() != null && getProfile() != null;
    }

    public Profile getProfile() {
        return Profile.getCurrentProfile();
    }

    public AccessToken getAccessToken() {
        return AccessToken.getCurrentAccessToken();
    }
}
