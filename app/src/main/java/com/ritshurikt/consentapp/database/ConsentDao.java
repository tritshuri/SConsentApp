package com.ritshurikt.consentapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ConsentDao {
    @Insert
    void insert(ConsentRecord record);

    @Query("SELECT * FROM consent_history WHERE userId = :userId ORDER BY timestamp DESC")
    LiveData<List<ConsentRecord>> getRecordsByUser(String userId);
}