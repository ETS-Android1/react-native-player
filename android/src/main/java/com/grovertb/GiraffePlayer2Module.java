package com.grovertb;

import android.net.Uri;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.grovertb.player.PlayerGlobal;
import com.grovertb.player.GiraffePlayer;
import com.grovertb.player.VideoInfo;

import java.util.HashMap;
import java.util.Map;



public class GiraffePlayer2Module extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private VideoInfo videoInfo;

    public GiraffePlayer2Module(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        videoInfo = new VideoInfo();
    }

    @Override
    public String getName() {
        return "GiraffePlayer2";
    }

    @Override
    public Map<String, Object> getConstants() {
//        final Map<String, Object> constants = new HashMap<>();
//        constants.put("AR_ASPECT_FIT_PARENT", 0);
//        constants.put("AR_ASPECT_FILL_PARENT", 1);
//        constants.put("AR_ASPECT_WRAP_CONTENT", 2);
//        constants.put("AR_MATCH_PARENT", 3);
//        constants.put("AR_16_9_FIT_PARENT", 4);
//        constants.put("AR_4_3_FIT_PARENT", 5);
//        return constants;
        return new HashMap<>();
    }

    @ReactMethod
    public void play(String url) {
//        videoInfo.setHeaders("{\"Access-Token\":\"66373.79.DXHQX9Z2P7RCVWQA43GHYE3YFMHM3UHR.f577b980bb7aff97fd15232caad68b18\", \"Client-Key\":\"YVLH48ZYDWVRGGSMRPCB34QSYGAH6U8U\"}");
        GiraffePlayer.play(reactContext, videoInfo.setUri(Uri.parse(url)));
    }


    @ReactMethod
    public void setTitle(String title) {
        videoInfo.setTitle(title);
    }

    @ReactMethod
    public void setHeaders(String headers) {
//        HashMap<String, String> newHeaders = new HashMap<String, String>();
//        newHeaders.put("Access-Token", "66373.79.DXHQX9Z2P7RCVWQA43GHYE3YFMHM3UHR.f577b980bb7aff97fd15232caad68b18");
//        newHeaders.put("Client-Key", "YVLH48ZYDWVRGGSMRPCB34QSYGAH6U8U");
        videoInfo.setHeaders(headers);
    }


    @ReactMethod
    public void setFullScreenOnly(Boolean isFullScreen) {
        videoInfo.setFullScreenOnly(isFullScreen);
    }


    @ReactMethod
    public void setShowTopBar(Boolean isShowTopBar) {
        videoInfo.setShowTopBar(isShowTopBar);
    }

    @ReactMethod
    public void onClose(Callback callback) {
        PlayerGlobal playerGlobal = new PlayerGlobal();
        playerGlobal.setCallbackFNC(callback);
    }


    @ReactMethod
    public void setAspectRatio(String aspectRatio) {
        int mAspectRatio;

        switch (aspectRatio) {
            case "AR_ASPECT_FIT_PARENT":
                mAspectRatio = VideoInfo.AR_ASPECT_FIT_PARENT;
                break;
            case "AR_ASPECT_FILL_PARENT":
                mAspectRatio = VideoInfo.AR_ASPECT_FILL_PARENT;
                break;
            case "AR_ASPECT_WRAP_CONTENT":
                mAspectRatio = VideoInfo.AR_ASPECT_WRAP_CONTENT;
                break;
            case "AR_16_9_FIT_PARENT":
                mAspectRatio = VideoInfo.AR_16_9_FIT_PARENT;
                break;
            case "AR_4_3_FIT_PARENT":
                mAspectRatio = VideoInfo.AR_4_3_FIT_PARENT;
                break;
            default: // "AR_MATCH_PARENT"
                mAspectRatio = VideoInfo.AR_MATCH_PARENT;
                break;
        }


        videoInfo.setAspectRatio(mAspectRatio);
    }
}
