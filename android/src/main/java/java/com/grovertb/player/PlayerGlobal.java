package com.grovertb.player;

import com.facebook.react.bridge.Callback;

public class PlayerGlobal {
    public static Callback callbackFNC = null;


    public void setCallbackFNC(Callback _value) {
        callbackFNC = _value;
        return;
    }

    public Callback getCallbackFNC() {
        return callbackFNC;
    }
}
