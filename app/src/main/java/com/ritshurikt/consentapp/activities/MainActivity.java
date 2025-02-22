package com.ritshurikt.consentapp.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.ritshurikt.consentapp.R;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        MaterialButton btnConsent = findViewById(R.id.btnConsent);
        MaterialButton btnHistory = findViewById(R.id.btnHistory);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);
        MaterialButton btnLearn = findViewById(R.id.btnLearn);

        btnLearn.setOnClickListener(v ->
                startActivity(new Intent(this, LearnActivity.class)));

        btnConsent.setOnClickListener(v ->
                startActivity(new Intent(this, ConsentActivity.class)));

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class)));

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}