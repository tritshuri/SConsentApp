package com.ritshurikt.consentapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ConsentDao {
    @Insert
    void insert(ConsentRequest request);

    @Update
    void update(ConsentRequest request);

    @Query("SELECT * FROM consent_requests WHERE receiverEmail = :email AND status = 'PENDING'")
    LiveData<List<ConsentRequest>> getPendingRequests(String email);

    @Query("SELECT * FROM consent_requests WHERE senderId = :userId ORDER BY timestamp DESC")
    LiveData<List<ConsentRequest>> getSentRequests(String userId);

    @Query("SELECT * FROM consent_requests WHERE senderId = :userId OR receiverEmail = :email ORDER BY timestamp DESC")
    LiveData<List<ConsentRequest>> getAllUserRequests(String userId, String email);
}