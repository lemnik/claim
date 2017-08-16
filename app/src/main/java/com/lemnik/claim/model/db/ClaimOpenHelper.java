package com.lemnik.claim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jason on 2017/08/13.
 */

public class ClaimOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public ClaimOpenHelper(final Context context) {
        super(context, "ClaimItems", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE claim_items (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "description TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "timestamp INTEGER NOT NULL," +
                "category TEXT NOT NULL)");

        db.execSQL("CREATE TABLE attachments (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "claim_item_id INTEGER NOT NULL," +
                "filename TEXT NOT NULL," +
                "type TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase db,
            final int oldVersion,
            final int newVersion) {

        final Upgrade[] upgrades = new Upgrade[]{
                null, // no version 0 upgrades
        };

        for (int version = oldVersion; version < newVersion; version++) {
            if (upgrades[version] != null)
                upgrades[version].exec(db);
        }
    }

    interface Upgrade {
        void exec(SQLiteDatabase db);
    }


    class StatementUpgrade implements Upgrade {
        final String sql;

        StatementUpgrade(final String sql) {
            this.sql = sql;
        }

        public void exec(final SQLiteDatabase db) {
            db.execSQL(sql);
        }
    }
}
