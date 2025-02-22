package com.ritshurikt.consentapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.ritshurikt.consentapp.database.AppDatabase;
import com.ritshurikt.consentapp.database.ConsentDao;
import com.ritshurikt.consentapp.database.ConsentRecord;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsentViewModel extends AndroidViewModel {
    private final ConsentDao consentDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public ConsentViewModel(Application application) {
        super(application);
        consentDao = AppDatabase.getInstance(application).consentDao();
    }

    public void insertConsent(String status) {
        executor.execute(() -> {
            if (auth.getCurrentUser() != null) {
                ConsentRecord record = new ConsentRecord(
                        status,
                        new Date(),
                        auth.getCurrentUser().getUid()
                );
                consentDao.insert(record);
            }
        });
    }

    public LiveData<List<ConsentRecord>> getConsentHistory() {
        String userId = auth.getCurrentUser() != null ?
                auth.getCurrentUser().getUid() : "";
        return consentDao.getRecordsByUser(userId);
    }

    @Override
    protected void onCleared() {
        executor.shutdown();
        super.onCleared();
    }
}