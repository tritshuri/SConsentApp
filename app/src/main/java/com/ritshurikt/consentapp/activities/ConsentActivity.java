package com.ritshurikt.consentapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ritshurikt.consentapp.R;
import com.ritshurikt.consentapp.database.ConsentRequest;
import com.ritshurikt.consentapp.viewmodel.ConsentViewModel;

import java.util.Date;

public class ConsentActivity extends AppCompatActivity {
    private ConsentViewModel viewModel;
    private TextInputEditText etReceiverEmail;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || !user.isEmailVerified()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(ConsentViewModel.class);
        progressBar = findViewById(R.id.progressBar);
        etReceiverEmail = findViewById(R.id.etReceiverEmail);

        MaterialButton btnSendRequest = findViewById(R.id.btnSendRequest);
        btnSendRequest.setOnClickListener(v -> sendConsentRequest());
    }

    private void sendConsentRequest() {
        String receiverEmail = etReceiverEmail.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && validateEmail(receiverEmail)) {
            progressBar.setVisibility(View.VISIBLE);

            ConsentRequest request = new ConsentRequest(
                    user.getUid(),
                    user.getEmail(),
                    receiverEmail,
                    "PENDING",
                    new Date()
            );

            viewModel.sendConsentRequest(request);

            Toast.makeText(this, "Request sent to " + receiverEmail, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            finish();
        }
    }

    private boolean validateEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etReceiverEmail.setError("Valid email required");
            return false;
        }
        return true;
    }
}