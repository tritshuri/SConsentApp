package com.ritshurikt.consentapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;

@Entity(tableName = "consent_history")
@TypeConverters(Converters.class)
public class ConsentRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String status;
    private Date timestamp;
    private String userId;

    public ConsentRecord(String status, Date timestamp, String userId) {
        this.status = status;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public int getId() { return id; }
    public String getStatus() { return status; }
    public Date getTimestamp() { return timestamp; }
    public String getUserId() { return userId; }

    public void setId(int id) { this.id = id; }
}