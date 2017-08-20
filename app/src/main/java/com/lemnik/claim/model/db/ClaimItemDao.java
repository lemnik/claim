package com.lemnik.claim.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lemnik.claim.model.ClaimItem;

import java.util.List;

@Dao
public interface ClaimItemDao {

    @Query("SELECT * FROM claimitem ORDER BY timestamp DESC")
    LiveData<List<ClaimItem>> selectAll();

    @Insert
    void insert(ClaimItem item);

    @Update
    void update(ClaimItem item);

    @Delete
    void delete(ClaimItem item);

}