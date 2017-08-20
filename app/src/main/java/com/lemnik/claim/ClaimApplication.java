package com.lemnik.claim;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.lemnik.claim.model.db.ClaimDatabase;


public class ClaimApplication extends Application {

    private ClaimDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, ClaimDatabase.class, "Claims").build();
    }

    public static ClaimDatabase getClaimDatabase(final Context context){
        return ((ClaimApplication)context.getApplicationContext()).database;
    }
}
