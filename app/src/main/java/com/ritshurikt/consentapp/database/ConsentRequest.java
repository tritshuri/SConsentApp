package com.ritshurikt.consentapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;

@Entity(tableName = "consent_requests")
@TypeConverters(Converters.class)
public class ConsentRequest {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String senderId;
    private String senderEmail;
    private String receiverEmail;
    private String status; // PENDING, ACCEPTED, DENIED
    private Date timestamp;

    public ConsentRequest(String senderId, String senderEmail,
                          String receiverEmail, String status, Date timestamp) {
        this.senderId = senderId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSenderId() { return senderId; }
    public String getSenderEmail() { return senderEmail; }
    public String getReceiverEmail() { return receiverEmail; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getTimestamp() { return timestamp; }
}