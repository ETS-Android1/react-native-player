package com.grovertb.player;

import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.bridge.Callback;

/**
 * Created by tcking on 2017
 */

public abstract class BasePlayerActivity extends AppCompatActivity {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        com.grovertb.player.PlayerManager.getInstance().onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (PlayerManager.getInstance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
