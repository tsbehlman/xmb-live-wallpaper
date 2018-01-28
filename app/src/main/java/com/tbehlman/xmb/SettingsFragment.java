package com.tbehlman.xmb;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.tbehlman.SeekBarPreference;

public class SettingsFragment extends PreferenceFragment {
    public static final String COLOR_KEY = "color";
    public static final String OPACITY_KEY = "opacity";
    public static final String COLOR_DEFAULT = "2559b3";
    public static final int OPACITY_DEFAULT = 75;
    public static final int OPACITY_MAX = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(getActivity());

        ListPreference colorPicker = new ListPreference(getActivity());
        colorPicker.setKey(COLOR_KEY);
        colorPicker.setEntries(R.array.color_entries);
        colorPicker.setEntryValues(R.array.color_values);
        colorPicker.setDefaultValue(COLOR_DEFAULT);
        colorPicker.setTitle(R.string.prefs_color);
        colorPicker.setSummary("%s");
        screen.addPreference(colorPicker);

        SeekBarPreference opacitySlider = new SeekBarPreference(getActivity());
        opacitySlider.setKey(OPACITY_KEY);
        opacitySlider.setMaximumValue(OPACITY_MAX);
        opacitySlider.setDefaultValue(OPACITY_DEFAULT);
        opacitySlider.setTitle(R.string.prefs_opacity);
        opacitySlider.setSummary(R.string.prefs_opacity_summary);
        screen.addPreference(opacitySlider);

        setPreferenceScreen(screen);
    }
}
