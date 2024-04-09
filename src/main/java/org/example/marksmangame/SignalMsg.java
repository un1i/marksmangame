package org.example.marksmangame;

public class SignalMsg {
    MsgAction action;
    boolean signal;

    SignalMsg(MsgAction action, boolean signal) {
        this.action = action;
        this.signal = signal;
    }

    public MsgAction get_action() {
        return action;
    }

    public boolean get_signal() {
        return signal;
    }
}
