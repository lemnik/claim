package com.lemnik.claim.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by jason on 2017/08/05.
 */

public class Bindings {

    @BindingAdapter("onFocusChanged")
    public static void bindOnFocusLostListener(final View view, final View.OnFocusChangeListener listener) {
        view.setOnFocusChangeListener(listener);
    }

}
