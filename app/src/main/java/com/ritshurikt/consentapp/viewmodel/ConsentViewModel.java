package com.ritshurikt.consentapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ritshurikt.consentapp.database.AppDatabase;
import com.ritshurikt.consentapp.database.ConsentDao;
import com.ritshurikt.consentapp.database.ConsentRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsentViewModel extends AndroidViewModel {
    private final ConsentDao consentDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public ConsentViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        consentDao = database.consentDao();
    }

    public void sendConsentRequest(ConsentRequest request) {
        executor.execute(() -> consentDao.insert(request));
    }

    public void respondToRequest(ConsentRequest request, boolean accepted) {
        executor.execute(() -> {
            request.setStatus(accepted ? "ACCEPTED" : "DENIED");
            consentDao.update(request);
        });
    }

    public LiveData<List<ConsentRequest>> getPendingRequests() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ?
                consentDao.getPendingRequests(user.getEmail()) :
                null;
    }

    public LiveData<List<ConsentRequest>> getSentRequests() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ?
                consentDao.getSentRequests(user.getUid()) :
                null;
    }

    public LiveData<List<ConsentRequest>> getAllUserRequests() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ?
                consentDao.getAllUserRequests(user.getUid(), user.getEmail()) :
                null;
    }

    @Override
    protected void onCleared() {
        executor.shutdown();
        super.onCleared();
    }
}