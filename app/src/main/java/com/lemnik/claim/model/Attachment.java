package com.lemnik.claim.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

@Entity(indices = @Index("claimItemId"))
public class Attachment implements Parcelable {

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

    @PrimaryKey(autoGenerate = true)
    public long id;
    public long claimItemId;

    public File file;

    public Type type;

    public Attachment() {
    }

    @Ignore
    public Attachment(final File file, final Type type) {
        this.file = file;
        this.type = type;
    }

    protected Attachment(final Parcel in) {
        id = in.readLong();
        claimItemId = in.readLong();
        file = new File(in.readString());
        type = Type.values()[in.readInt()];
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClaimItemId() {
        return claimItemId;
    }

    public void setClaimItemId(long claimItemId) {
        this.claimItemId = claimItemId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = Type.safe(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

@Override
public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeLong(id);
    dest.writeLong(claimItemId);
    dest.writeString(file.getAbsolutePath());
    dest.writeInt(type.ordinal());
}

    public enum Type {
        IMAGE,
        UNKNOWN;

        public static Type safe(final Type type) {
            return type != null ? type : UNKNOWN;
        }
    }

}
