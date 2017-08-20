package com.lemnik.claim.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lemnik.claim.model.Attachment;

import java.util.List;

@Dao
public interface AttachmentDao {

    @Query("SELECT * FROM attachment WHERE claimItemId = :claimItemId")
    LiveData<List<Attachment>> selectForClaimItemId(final long claimItemId);

    @Insert
    void insert(Attachment attachment);

    @Update
    void update(Attachment attachment);

    @Update
    void delete(Attachment attachment);

}
