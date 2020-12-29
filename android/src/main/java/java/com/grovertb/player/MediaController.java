package com.grovertb.player;


import com.grovertb.player.PlayerListener;
import com.grovertb.player.VideoView;

/**
 * Created by tcking on 2017
 */

public interface MediaController extends PlayerListener {
    /**
     * bind this media controller to video controllerView
     */
    void bind(VideoView videoView);
}
