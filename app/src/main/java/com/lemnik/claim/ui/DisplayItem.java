package com.lemnik.claim.ui;

/**
 * Created by jason on 2017/09/28.
 */
public class DisplayItem {

    public final int layout;

    public final Object value;

    public DisplayItem(
            final int layout,
            final Object value) {

        this.layout = layout;
        this.value = value;
    }

    public <I> void bindItem(final DataBoundViewHolder<?, I> holder) {
        @SuppressWarnings("unchecked") final I item = (I) value;
        holder.setItem(item);
    }

}
