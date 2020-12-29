package com.grovertb.player;

import com.grovertb.player.GiraffePlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

/**
 * Created by tcking on 2017
 */

public interface PlayerListener {

    void onPrepared(com.grovertb.player.GiraffePlayer giraffePlayer);

    /**
     * Called to update status in buffering a media stream received through progressive HTTP download.
     * @param giraffePlayer
     * @param percent nt: the percentage (0-100) of the content that has been buffered or played thus far
     */
    void onBufferingUpdate(com.grovertb.player.GiraffePlayer giraffePlayer, int percent);

    boolean onInfo(com.grovertb.player.GiraffePlayer giraffePlayer, int what, int extra);

    void onCompletion(com.grovertb.player.GiraffePlayer giraffePlayer);

    void onSeekComplete(com.grovertb.player.GiraffePlayer giraffePlayer);

    boolean onError(com.grovertb.player.GiraffePlayer giraffePlayer, int what, int extra);

    void onPause(com.grovertb.player.GiraffePlayer giraffePlayer);

    void onRelease(com.grovertb.player.GiraffePlayer giraffePlayer);

    void onStart(com.grovertb.player.GiraffePlayer giraffePlayer);

    void onTargetStateChange(int oldState, int newState);

    void onCurrentStateChange(int oldState, int newState);

    void onDisplayModelChange(int oldModel, int newModel);

    void onPreparing(com.grovertb.player.GiraffePlayer giraffePlayer);

    /**
     * render subtitle
     * @param giraffePlayer
     * @param text
     */
    void onTimedText(com.grovertb.player.GiraffePlayer giraffePlayer, IjkTimedText text);

    void onLazyLoadProgress(com.grovertb.player.GiraffePlayer giraffePlayer, int progress);

    void onLazyLoadError(GiraffePlayer giraffePlayer, String message);
}
