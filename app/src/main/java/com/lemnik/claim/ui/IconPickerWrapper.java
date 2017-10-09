package com.lemnik.claim.ui;

import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

/**
 * Created by jason on 2017/07/08.
 */

public class IconPickerWrapper implements RadioGroup.OnCheckedChangeListener {

    private final TextSwitcher label;

    public IconPickerWrapper(final TextSwitcher label) {
        this.label = label;
    }

    public void setLabelText(final CharSequence text) {
        label.setText(text);
    }

    @Override
    public void onCheckedChanged(final RadioGroup group, final int checkedId) {
        setLabelText(group.findViewById(checkedId).getContentDescription());
    }
}
