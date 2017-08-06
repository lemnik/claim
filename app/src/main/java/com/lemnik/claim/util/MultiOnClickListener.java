package com.lemnik.claim.util;

import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jason on 2017/07/06.
 */

public class MultiOnClickListener implements View.OnClickListener {

    private final List<View.OnClickListener> listeners = new CopyOnWriteArrayList<>();

    public MultiOnClickListener(final View.OnClickListener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    @Override
    public void onClick(View v) {
        for (final View.OnClickListener listener : listeners) {
            listener.onClick(v);
        }
    }

    public void addOnClickListener(final View.OnClickListener listener) {
        if (listener == null) {
            return;
        }

        listeners.add(listener);
    }

    public void removeOnClickListener(final View.OnClickListener listener) {
        if (listener == null) {
            return;
        }

        listeners.remove(listener);
    }
}
