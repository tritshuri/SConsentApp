package com.ritshurikt.consentapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.ritshurikt.consentapp.R;
import com.ritshurikt.consentapp.viewmodel.ConsentViewModel;

public class ConsentActivity extends AppCompatActivity {
    private ConsentViewModel viewModel;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);

        auth = FirebaseAuth.getInstance();
        if (!isUserValid()) {
            redirectToLogin();
            return;
        }

        viewModel = new ViewModelProvider(this).get(ConsentViewModel.class);
        progressBar = findViewById(R.id.progressBar);

        MaterialButton btnYes = findViewById(R.id.btnYes);
        MaterialButton btnNo = findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> handleConsent("CONSENT_GRANTED"));
        btnNo.setOnClickListener(v -> handleConsent("CONSENT_DENIED"));
    }

    private boolean isUserValid() {
        return auth.getCurrentUser() != null &&
                auth.getCurrentUser().isEmailVerified();
    }

    private void redirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void handleConsent(String status) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.insertConsent(status);

        String message = status.equals("CONSENT_GRANTED") ?
                "Consent confirmed. Remember, It can be WITHDRAWN at any time." :
                "Consent not given. Decision respected.";

        startActivity(new Intent(this, ConfirmationActivity.class)
                .putExtra("CONFIRMATION_MESSAGE", message));

        progressBar.setVisibility(View.GONE);
        finish();
    }
}