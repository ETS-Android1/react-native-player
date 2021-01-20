package com.grovertb.player;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.grovertb.R;


/**
 * Created by tcking on 2017
 */

public class PlayerActivity extends BasePlayerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giraffe_player_activity);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        VideoInfo videoInfo = intent.getParcelableExtra("__video_info__");
        if (videoInfo == null) {
            finish();
            return;
        }
        if(videoInfo.isFullScreenOnly()){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        com.grovertb.player.PlayerManager.getInstance().releaseByFingerprint(videoInfo.getFingerprint());
        VideoView videoView = findViewById(R.id.video_view);
        videoView.videoInfo(videoInfo);
        PlayerManager.getInstance().getPlayer(videoView).start();
    }
    
    @Override
    public void onBackPressed() {
        PlayerGlobal playerGlobal = new PlayerGlobal();
        Callback callbackOnClose = playerGlobal.getCallbackFNC();
        if (callbackOnClose != null) {
            callbackOnClose.invoke();
        }
        super.onBackPressed();
    }

}
