package com.lemnik.claim.model.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.lemnik.claim.model.Attachment;
import com.lemnik.claim.model.Category;
import com.lemnik.claim.model.ClaimItem;

import java.io.File;
import java.util.Date;

@Database(
        entities = {ClaimItem.class, Attachment.class},
        version = 1,
        exportSchema = false)
@TypeConverters(ClaimDatabase.class)
public abstract class ClaimDatabase extends RoomDatabase {

    public abstract ClaimItemDao claimItemDao();

    public abstract AttachmentDao attachmentDao();

    public Runnable createClaimItemTask(final ClaimItem claimItem) {
        return new Runnable() {
            @Override
            public void run() {
                beginTransaction();
                try {
                    final long claimId = claimItemDao().insert(claimItem);
                    claimItem.id = claimId;

                    for (final Attachment attachment : claimItem.getAttachments()) {
                        attachment.claimItemId = claimId;
                        attachment.id = attachmentDao().insert(attachment);
                    }
                    setTransactionSuccessful();
                } finally {
                    endTransaction();
                }
            }
        };
    }

    @TypeConverter
    public static Long fromDate(final Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date toDate(final Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String fromFile(final File value) {
        return value == null ? null : value.getAbsolutePath();
    }

    @TypeConverter
    public static File toFile(final String path) {
        return path == null ? null : new File(path);
    }

    @TypeConverter
    public static String fromCategory(final Category value) {
        return value == null ? null : value.name();
    }

    @TypeConverter
    public static Category toCategory(final String name) {
        return name == null ? null : Category.valueOf(name);
    }

    @TypeConverter
    public static String fromAttachmentType(final Attachment.Type value) {
        return value == null ? null : value.name();
    }

    @TypeConverter
    public static Attachment.Type toAttachmentType(final String name) {
        return name == null ? null : Attachment.Type.valueOf(name);
    }
}
