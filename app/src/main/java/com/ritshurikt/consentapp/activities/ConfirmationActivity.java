package com.ritshurikt.consentapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.ritshurikt.consentapp.R;

public class ConfirmationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        TextView tvConfirmation = findViewById(R.id.tvConfirmation);
        MaterialButton btnDone = findViewById(R.id.btnDone);

        // Get message from intent
        String message = getIntent().getStringExtra("CONFIRMATION_MESSAGE");
        if(message != null) {
            tvConfirmation.setText(message);
        }

        btnDone.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}