package com.grovertb.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.facebook.react.bridge.Callback;
import com.grovertb.player.DefaultMediaController;
import com.grovertb.player.GiraffePlayer;
import com.grovertb.player.MediaController;
import com.grovertb.player.VideoInfo;
import com.grovertb.player.VideoView;


/**
 * Created by tcking on 2017
 */

public class PlayerManager {

    public static final String TAG = "GiraffePlayerManager";
    private volatile String currentPlayerFingerprint;
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
    private WeakReference<Activity> topActivityRef;

    public com.grovertb.player.VideoInfo getDefaultVideoInfo() {
        return defaultVideoInfo;
    }

    /**
     * default config for all
     */
    private final com.grovertb.player.VideoInfo defaultVideoInfo = new com.grovertb.player.VideoInfo();


    public PlayerManager.MediaControllerGenerator getMediaControllerGenerator() {
        return MediaControllerGenerator;
    }

    public void setMediaControllerGenerator(PlayerManager.MediaControllerGenerator mediaControllerGenerator) {
        MediaControllerGenerator = mediaControllerGenerator;
    }

    private MediaControllerGenerator MediaControllerGenerator = new MediaControllerGenerator() {

        @Override
        public MediaController create(Context context, com.grovertb.player.VideoInfo videoInfo) {
            return new DefaultMediaController(context);
        }
    };

    private WeakHashMap<String, com.grovertb.player.VideoView> videoViewsRef = new WeakHashMap<>();
    private Map<String, com.grovertb.player.GiraffePlayer> playersRef = new ConcurrentHashMap<>();
    private WeakHashMap<Context, String> activity2playersRef = new WeakHashMap<>();


    private static final PlayerManager instance = new PlayerManager();


    public static PlayerManager getInstance() {
        return instance;
    }

    public com.grovertb.player.GiraffePlayer getCurrentPlayer() {
        return currentPlayerFingerprint == null ? null : playersRef.get(currentPlayerFingerprint);
    }

    private com.grovertb.player.GiraffePlayer createPlayer(com.grovertb.player.VideoView videoView) {
        com.grovertb.player.VideoInfo videoInfo = videoView.getVideoInfo();
        log(videoInfo.getFingerprint(), "createPlayer");
        videoViewsRef.put(videoInfo.getFingerprint(), videoView);
        registerActivityLifecycleCallbacks(((Activity) videoView.getContext()).getApplication());
        com.grovertb.player.GiraffePlayer player = com.grovertb.player.GiraffePlayer.createPlayer(videoView.getContext(), videoInfo);
        playersRef.put(videoInfo.getFingerprint(), player);
        activity2playersRef.put(videoView.getContext(), videoInfo.getFingerprint());
        if (topActivityRef == null || topActivityRef.get() == null) {
            topActivityRef = new WeakReference<>((Activity) videoView.getContext());
        }
        return player;
    }

    private synchronized void registerActivityLifecycleCallbacks(Application context) {
        if (activityLifecycleCallbacks != null) {
            return;
        }
        activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
//                System.out.println("======onActivityStarted============"+activity);

            }

            @Override
            public void onActivityResumed(Activity activity) {
//                System.out.println("======onActivityResumed============"+activity);

                com.grovertb.player.GiraffePlayer currentPlayer = getPlayerByFingerprint(activity2playersRef.get(activity));
                if (currentPlayer != null) {
                    currentPlayer.onActivityResumed();
                }
                topActivityRef = new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
//                System.out.println("======onActivityPaused============"+activity);
                com.grovertb.player.GiraffePlayer currentPlayer = getPlayerByFingerprint(activity2playersRef.get(activity));
                if (currentPlayer != null) {
                    currentPlayer.onActivityPaused();
                }
                if (topActivityRef != null && topActivityRef.get() == activity) {
                    topActivityRef.clear();
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
//                System.out.println("======onActivityStopped============"+activity);

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                com.grovertb.player.GiraffePlayer currentPlayer = getPlayerByFingerprint(activity2playersRef.get(activity));
                if (currentPlayer != null) {
                    currentPlayer.onActivityDestroyed();
                }
                activity2playersRef.remove(activity);
            }
        };
        context.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void releaseCurrent() {
        log(currentPlayerFingerprint, "releaseCurrent");
        com.grovertb.player.GiraffePlayer currentPlayer = getCurrentPlayer();
        if (currentPlayer != null) {
            if (currentPlayer.getProxyPlayerListener() != null) {
                currentPlayer.getProxyPlayerListener().onCompletion(currentPlayer);
            }
            currentPlayer.release();
        }
        currentPlayerFingerprint = null;
    }


    public boolean isCurrentPlayer(String fingerprint) {
        return fingerprint != null && fingerprint.equals(this.currentPlayerFingerprint);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        com.grovertb.player.GiraffePlayer currentPlayer = getCurrentPlayer();
        if (currentPlayer != null) {
            currentPlayer.onConfigurationChanged(newConfig);
        }
    }

    public boolean onBackPressed() {
        com.grovertb.player.GiraffePlayer currentPlayer = getCurrentPlayer();
        if (currentPlayer != null) {
            return currentPlayer.onBackPressed();
        }
        return false;
    }

    public com.grovertb.player.VideoView getVideoView(com.grovertb.player.VideoInfo videoInfo) {
        return videoViewsRef.get(videoInfo.getFingerprint());
    }


    public void setCurrentPlayer(com.grovertb.player.GiraffePlayer giraffePlayer) {
        com.grovertb.player.VideoInfo videoInfo = giraffePlayer.getVideoInfo();
        log(videoInfo.getFingerprint(), "setCurrentPlayer");

        //if choose a new playerRef
        String fingerprint = videoInfo.getFingerprint();
        if (!isCurrentPlayer(fingerprint)) {
            try {
                log(videoInfo.getFingerprint(), "not same release before one:" + currentPlayerFingerprint);
                releaseCurrent();
                currentPlayerFingerprint = fingerprint;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            log(videoInfo.getFingerprint(), "is currentPlayer");
        }
    }

    public com.grovertb.player.GiraffePlayer getPlayer(VideoView videoView) {
        com.grovertb.player.VideoInfo videoInfo = videoView.getVideoInfo();
        com.grovertb.player.GiraffePlayer player = playersRef.get(videoInfo.getFingerprint());
        if (player == null) {
            player = createPlayer(videoView);
        }
        return player;
    }

    public com.grovertb.player.GiraffePlayer getPlayerByFingerprint(String fingerprint) {
        if (fingerprint == null) {
            return null;
        }
        return playersRef.get(fingerprint);
    }

    public PlayerManager releaseByFingerprint(String fingerprint) {
        com.grovertb.player.GiraffePlayer player = playersRef.get(fingerprint);
        if (player != null) {
            player.release();
        }
        return this;
    }

    public void removePlayer(String fingerprint) {
        playersRef.remove(fingerprint);
    }

    private void log(String fingerprint, String msg) {
        if (GiraffePlayer.debug) {
            Log.d(TAG, String.format("[setFingerprint:%s] %s", fingerprint, msg));
        }
    }

    public Activity getTopActivity() {
        return topActivityRef.get();
    }


    /**
     * to create a custom MediaController
     */
    public interface MediaControllerGenerator {
        /**
         * called when VideoView need a MediaController
         * @param context
         * @param videoInfo
         * @return
         */
        MediaController create(Context context, VideoInfo videoInfo);
    }
}
