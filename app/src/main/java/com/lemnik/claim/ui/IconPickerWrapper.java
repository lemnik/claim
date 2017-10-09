package com.lemnik.claim.ui;

import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

/**
 * Created by jason on 2017/07/08.
 */
public abstract class IconPickerWrapper implements RadioGroup.OnCheckedChangeListener {

    public abstract void setLabelText(final CharSequence text);

    @Override
    public void onCheckedChanged(final RadioGroup group, final int checkedId) {
        setLabelText(group.findViewById(checkedId).getContentDescription());
    }

    public static IconPickerWrapper create(final TextView label) {
        return new IconPickerWrapper() {
            public void setLabelText(final CharSequence text) {
                label.setText(text);
            }
        };
    }

    public static IconPickerWrapper create(final TextSwitcher label) {
        return new IconPickerWrapper() {
            public void setLabelText(final CharSequence text) {
                label.setText(text);
            }
        };
    }
}
