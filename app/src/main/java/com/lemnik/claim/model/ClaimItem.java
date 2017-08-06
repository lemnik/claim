package com.lemnik.claim.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ClaimItem implements Parcelable {

    public static final Creator<ClaimItem> CREATOR = new Creator<ClaimItem>() {
        @Override
        public ClaimItem createFromParcel(Parcel in) {
            return new ClaimItem(in);
        }

        @Override
        public ClaimItem[] newArray(int size) {
            return new ClaimItem[size];
        }
    };

    String description;
    double amount;
    Date timestamp;
    Category category;
    List<Attachment> attachments = new ArrayList<>();

    public ClaimItem() {
    }

    ClaimItem(
            final String description,
            final double amount,
            final Date timestamp,
            final Category category) {

        this.description = description;
        this.amount = amount;
        this.timestamp = timestamp;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void addAttachment(final Attachment attachment) {
        if ((attachment != null) && !attachments.contains(attachment)) {
            attachments.add(attachment);
        }
    }

    public void removeAttachment(final Attachment attachment) {
        attachments.remove(attachment);
    }

    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    protected ClaimItem(final Parcel in) {
        description = in.readString();
        amount = in.readDouble();

        final long time = in.readLong();
        timestamp = time != -1 ? new Date(time) : null;

        final int categoryOrd = in.readInt();
        category = categoryOrd != -1 ? Category.values()[categoryOrd] : null;

        in.readTypedList(attachments, Attachment.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeLong(timestamp != null ? timestamp.getTime() : -1);
        dest.writeInt(category != null ? category.ordinal() : -1);
        dest.writeTypedList(attachments);
    }

}