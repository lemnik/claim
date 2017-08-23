package com.lemnik.claim;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.lemnik.claim.model.db.ClaimDatabase;

public class ClaimApplication extends Application {

    private static ClaimDatabase DATABASE;

    @Override
    public void onCreate() {
        super.onCreate();
        DATABASE = Room.databaseBuilder(
            /* Context */this,
            /* Abstract Database Class */ ClaimDatabase.class,
            /* Filename */ "Claims"
        ).build();
    }

    public static ClaimDatabase getClaimDatabase() {
        return DATABASE;
    }

}
