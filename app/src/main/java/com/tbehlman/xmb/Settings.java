package com.tbehlman.xmb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import static com.tbehlman.xmb.SettingsFragment.*;

public class Settings {
    private OnSharedPreferenceChangeListener preferenceListener;
    private SharedPreferences preferences;
    private float[] color;
    private float opacity;

    public Settings(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        updateColorFromPreferences();
        updateOpacityFromPreferences();
        onCreate();
    }

    public float[] getColor() {
        return color;
    }

    public float getOpacity() {
        return opacity;
    }

    private void updateColorFromPreferences() {
        color = Color.fromHex(preferences.getString(COLOR_KEY, COLOR_DEFAULT));
    }

    private void updateOpacityFromPreferences() {
        opacity = (float) preferences.getInt(OPACITY_KEY, OPACITY_DEFAULT) / OPACITY_MAX;
    }

    public void onCreate() {
        if (preferenceListener != null) {
            return;
        }
        preferenceListener = new OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(COLOR_KEY)) {
                    Settings.this.updateColorFromPreferences();
                }
                else if (key.equals(OPACITY_KEY)) {
                    Settings.this.updateOpacityFromPreferences();
                }
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(preferenceListener);
    }

    public void onDestroy() {
        preferences.unregisterOnSharedPreferenceChangeListener(preferenceListener);
        preferenceListener = null;
    }
}
