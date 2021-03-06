package com.lemnik.claim.ui;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;

public class DataBoundViewHolder<P, I> extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    private I item;

    public DataBoundViewHolder(final ViewDataBinding binding, final P presenter) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setVariable(BR.presenter, presenter);
    }

    public void setItem(final I item) {
        this.item = item;
        binding.setVariable(BR.item, item);
    }

    public I getItem() {
        return item;
    }

    public void setPresenter(final P presenter) {
        binding.setVariable(BR.presenter, presenter);
    }

}
