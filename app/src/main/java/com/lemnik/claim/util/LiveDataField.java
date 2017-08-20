package com.lemnik.claim.util;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;


public class LiveDataField<T> extends ObservableField<T> implements Observer<T> {

    public LiveDataField(final LifecycleOwner owner, final LiveData<T> liveData) {
        liveData.observe(owner, this);

    }

    @Override
    public void onChanged(final @Nullable T t) {
        set(t); // capture and update our listeners
    }
}
