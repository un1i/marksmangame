package org.example.marksmangame;

public class AuthResponse {
    boolean result;
    String text;

    AuthResponse(boolean is_connected, String text) {
        result = is_connected;
        this.text = text;
    }

    public boolean is_connected() {
        return result;
    }

    public String get_text() {
        return text;
    }
}
