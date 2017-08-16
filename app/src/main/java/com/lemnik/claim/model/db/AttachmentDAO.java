package com.lemnik.claim.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lemnik.claim.model.Attachment;

import java.io.File;

public class AttachmentDAO {

    public static Attachment fromCursor(final Cursor cursor) {
        return fromCursor(
                cursor,
                cursor.getColumnIndex("_id"),
                cursor.getColumnIndex("filename"),
                cursor.getColumnIndex("type"));
    }

    public static Attachment fromCursor(
            final Cursor cursor,
            final int idColIdx,
            final int filenameColIdx,
            final int typeColIdx) {

        return new Attachment(
                cursor.getLong(idColIdx),
                new File(cursor.getString(filenameColIdx)),
                Attachment.Type.valueOf(cursor.getString(typeColIdx))
        );
    }

    public static long insert(
            final Attachment attachment,
            final long claimItemId,
            final SQLiteDatabase database) {

        final ContentValues values = new ContentValues();
        values.put("filename", attachment.getFile().getAbsolutePath());
        values.put("type", attachment.getType().name());
        values.put("claim_item_id", claimItemId);

        return database.insert("attachments", null, values);
    }

    public static void update(
            final Attachment attachment,
            final SQLiteDatabase database
    ) {

        final ContentValues values = new ContentValues();
        values.put("filename", attachment.getFile().getAbsolutePath());
        values.put("type", attachment.getType().name());

        database.update("attachments", values, "id=?", new String[]{Long.toString(attachment.getId())});
    }

}
