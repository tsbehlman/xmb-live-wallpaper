package com.tbehlman;

import android.content.Context;
import android.preference.Preference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.*;
import static android.widget.RelativeLayout.*;

public class SeekBarPreference extends Preference implements OnSeekBarChangeListener {
    protected SeekBar seekBar;
    protected int maximumValue;
    protected int value;
    protected TextView summaryView;
    protected String summary = "";

    public SeekBarPreference(Context context) {
        super(context);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        try {
            value = Integer.parseInt(String.valueOf(defaultValue));
        } catch (Exception e) {
            value = getPersistedInt(value);
        }
        setValue(value);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);

        // Remove the default layout which is positioned beside the text
        View linearLayout = view.findViewById(android.R.id.widget_frame);
        ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);

        TextView summaryView = (TextView) view.findViewById(android.R.id.summary);

        ViewGroup layout = (ViewGroup) summaryView.getParent();

        SeekBar seekBar = new SeekBar(getContext());
        LayoutParams seekBarLayoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        seekBarLayoutParams.addRule(BELOW, android.R.id.summary);
        seekBarLayoutParams.addRule(ALIGN_START, android.R.id.title);
        seekBar.setId(android.R.id.progress);
        seekBar.setLayoutParams(seekBarLayoutParams);
        seekBar.setFocusable(true);
        seekBar.setClickable(true);
        layout.addView(seekBar);

        return view;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        summaryView = (TextView) view.findViewById(android.R.id.summary);
        seekBar = (SeekBar) view.findViewById(android.R.id.progress);
        seekBar.setMax(maximumValue);
        seekBar.setProgress(value);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            setValue(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public CharSequence getSummary() {
        return String.format(summary, value);
    }

    @Override
    public void setSummary(CharSequence summary) {
        this.summary = String.valueOf(summary);
        notifyChanged();
    }

    protected void setValue(int value) {
        if (value != this.value && callChangeListener(value)) {
            this.value = value;
            if(shouldPersist()) {
                persistInt(value);
            }
            if (summaryView != null) {
                summaryView.setText(getSummary());
            }
        }
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }
}
