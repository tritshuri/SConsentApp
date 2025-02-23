package com.ritshurikt.consentapp.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

public class SendConsentActivity extends AppCompatActivity {
    private ConsentViewModel viewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_consent);

        auth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(ConsentViewModel.class);

        TextInputEditText etReceiverEmail = findViewById(R.id.etReceiverEmail);
        MaterialButton btnSend = findViewById(R.id.btnSendRequest);

        btnSend.setOnClickListener(v -> {
            String receiverEmail = etReceiverEmail.getText().toString().trim();
            if (validateEmail(receiverEmail)) {
                sendConsentRequest(receiverEmail);
            }
        });
    }

    private boolean validateEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ((TextInputEditText) findViewById(R.id.etReceiverEmail))
                    .setError("Invalid email address");
            return false;
        }
        return true;
    }

    private void sendConsentRequest(String receiverEmail) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            ConsentRequest request = new ConsentRequest(
                    user.getUid(),
                    user.getEmail(),
                    receiverEmail,
                    "PENDING",
                    new Date()
            );
            viewModel.sendConsentRequest(request);
            Toast.makeText(this, "Request sent successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "You must be verified to send requests", Toast.LENGTH_LONG).show();
        }
    }
}