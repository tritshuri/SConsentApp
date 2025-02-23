package com.ritshurikt.consentapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ritshurikt.consentapp.R;
import com.ritshurikt.consentapp.adapters.PendingRequestsAdapter;
import com.ritshurikt.consentapp.database.ConsentRequest;
import com.ritshurikt.consentapp.viewmodel.ConsentViewModel;

public class PendingRequestsActivity extends AppCompatActivity
        implements PendingRequestsAdapter.OnResponseListener {

    private ConsentViewModel viewModel;
    private PendingRequestsAdapter adapter;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

        // Check authentication
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null || !currentUser.isEmailVerified()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setupRecyclerView();
        setupViewModel();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerPendingRequests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new PendingRequestsAdapter();
        adapter.setOnResponseListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ConsentViewModel.class);
        viewModel.getPendingRequests().observe(this, requests -> {
            if (requests != null) {
                adapter.submitList(requests);
            }
        });
    }

    @Override
    public void onResponse(ConsentRequest request, boolean accepted) {
        if (isValidResponse(request)) {
            viewModel.respondToRequest(request, accepted);
            showResponseToast(accepted);
        }
    }

    private boolean isValidResponse(ConsentRequest request) {
        // Verify the responding user is the intended receiver
        return request != null &&
                request.getReceiverEmail().equalsIgnoreCase(currentUser.getEmail());
    }

    private void showResponseToast(boolean accepted) {
        String message = accepted ?
                "Consent request accepted" :
                "Consent request denied";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.setOnResponseListener(null);
    }
}