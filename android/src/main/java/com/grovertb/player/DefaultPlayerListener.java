package com.grovertb.player;

import com.grovertb.player.GiraffePlayer;
import com.grovertb.player.PlayerListener;
import tv.danmaku.ijk.media.player.IjkTimedText;

/**
 * Created by tcking on 2017
 */

public class DefaultPlayerListener implements PlayerListener {

    public static final DefaultPlayerListener INSTANCE = new DefaultPlayerListener();

    @Override
    public void onPrepared(com.grovertb.player.GiraffePlayer giraffePlayer) {

    }

    @Override
    public void onBufferingUpdate(com.grovertb.player.GiraffePlayer giraffePlayer, int percent) {

    }

    @Override
    public boolean onInfo(com.grovertb.player.GiraffePlayer giraffePlayer, int what, int extra) {
        return true;
    }

    @Override
    public void onCompletion(com.grovertb.player.GiraffePlayer giraffePlayer) {

    }

    @Override
    public void onSeekComplete(com.grovertb.player.GiraffePlayer giraffePlayer) {

    }

    @Override
    public boolean onError(com.grovertb.player.GiraffePlayer giraffePlayer, int what, int extra) {
        return true;
    }

    @Override
    public void onPause(com.grovertb.player.GiraffePlayer giraffePlayer) {

    }

    @Override
    public void onRelease(com.grovertb.player.GiraffePlayer giraffePlayer) {

    }

    @Override
    public void onStart(com.grovertb.player.GiraffePlayer giraffePlayer) {

    }

    @Override
    public void onTargetStateChange(int oldState, int newState) {

    }

    @Override
    public void onCurrentStateChange(int oldState, int newState) {

    }

    @Override
    public void onDisplayModelChange(int oldModel, int newModel) {

    }

    @Override
    public void onPreparing(com.grovertb.player.GiraffePlayer giraffePlayer) {

    }

    @Override
    public void onTimedText(com.grovertb.player.GiraffePlayer giraffePlayer, IjkTimedText text) {

    }

    @Override
    public void onLazyLoadError(com.grovertb.player.GiraffePlayer giraffePlayer, String message) {

    }

    @Override
    public void onLazyLoadProgress(GiraffePlayer giraffePlayer, int progress) {

    }
}
