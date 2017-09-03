package com.lemnik.claim.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemnik.claim.R;
import com.lemnik.claim.model.Category;
import com.lemnik.claim.model.ClaimItem;

import java.text.DateFormat;

/**
 * Created by jason on 2017/08/26.
 */
public class ClaimItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView categoryIcon;
    private final TextView description;
    private final TextView amount;
    private final TextView timestamp;
    private final DateFormat dateFormat;

    public ClaimItemViewHolder(final View claimItemCard) {
        super(claimItemCard);
        this.categoryIcon = claimItemCard.findViewById(R.id.category_icon);
        this.description = claimItemCard.findViewById(R.id.item_description);
        this.amount = claimItemCard.findViewById(R.id.item_amount);
        this.timestamp = claimItemCard.findViewById(R.id.item_timestamp);

        this.dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    }

    public Drawable getCategoryIcon(final Category category) {
        final Resources resources = itemView.getResources();
        switch (category) {
            case ACCOMMODATION:
                return resources.getDrawable(R.drawable.ic_hotel_black);
            case FOOD:
                return resources.getDrawable(R.drawable.ic_food_black);
            case TRANSPORT:
                return resources.getDrawable(R.drawable.ic_transport_black);
            case ENTERTAINMENT:
                return resources.getDrawable(R.drawable.ic_entertainment_black);
            case BUSINESS:
                return resources.getDrawable(R.drawable.ic_business_black);
            case OTHER:
            default:
                return resources.getDrawable(R.drawable.ic_other_black);
        }
    }

    public String formatAmount(final double amount) {
        return amount == 0
                ? ""
                : amount == (int) amount
                ? Integer.toString((int) amount)
                : String.format("%.2f", amount);
    }

    public void setClaimItem(final ClaimItem item) {
        categoryIcon.setImageDrawable(getCategoryIcon(item.getCategory()));
        description.setText(item.getDescription());
        amount.setText(formatAmount(item.getAmount()));
        timestamp.setText(dateFormat.format(item.getTimestamp()));
    }

}
