package com.lemnik.claim.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lemnik.claim.R;
import com.lemnik.claim.model.ClaimItem;
import com.lemnik.claim.ui.presenters.ItemPresenter;

import java.util.Collections;
import java.util.List;

public class ClaimItemAdapter extends RecyclerView.Adapter<DataBoundViewHolder<ItemPresenter, ClaimItem>> {

    final LayoutInflater layoutInflater;
    final ItemPresenter itemPresenter;
    List<ClaimItem> items = Collections.emptyList();

    public ClaimItemAdapter(
            final Context context,
            final LifecycleOwner owner,
            final LiveData<List<ClaimItem>> liveItems) {

        this.layoutInflater = LayoutInflater.from(context);
        this.itemPresenter = new ItemPresenter(context);

        liveItems.observe(owner, new Observer<List<ClaimItem>>() {
            @Override
            public void onChanged(@Nullable List<ClaimItem> claimItems) {
                ClaimItemAdapter.this.items = (claimItems != null)
                        ? claimItems
                        : Collections.emptyList();
                ClaimItemAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public DataBoundViewHolder<ItemPresenter, ClaimItem> onCreateViewHolder(
            final ViewGroup parent,
            final int viewType) {

        return new DataBoundViewHolder<>(
                DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.card_claim_item,
                        parent,
                        false
                ),
                itemPresenter
        );
    }

    @Override
    public void onBindViewHolder(
            final DataBoundViewHolder<ItemPresenter, ClaimItem> holder,
            final int position) {

        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
