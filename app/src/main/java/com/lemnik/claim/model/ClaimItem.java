package com.lemnik.claim.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
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

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String description;
    public double amount;
    public Date timestamp;
    public Category category;

    @Ignore
    List<Attachment> attachments = new ArrayList<>();

    public ClaimItem() {
    }

    @Ignore
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
        id = in.readLong();
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
        dest.writeLong(id);
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeLong(timestamp != null ? timestamp.getTime() : -1);
        dest.writeInt(category != null ? category.ordinal() : -1);
        dest.writeTypedList(attachments);
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(description)
                && amount > 0
                && timestamp != null
                && category != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClaimItem claimItem = (ClaimItem) o;

        if (id != claimItem.id) return false;
        if (Double.compare(claimItem.amount, amount) != 0) return false;
        if (description != null ? !description.equals(claimItem.description) : claimItem.description != null)
            return false;
        if (timestamp != null ? !timestamp.equals(claimItem.timestamp) : claimItem.timestamp != null)
            return false;
        if (category != claimItem.category) return false;
        return attachments != null ? attachments.equals(claimItem.attachments) : claimItem.attachments == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        return result;
    }
}
