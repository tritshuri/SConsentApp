package com.ritshurikt.consentapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.ritshurikt.consentapp.R;
import com.ritshurikt.consentapp.adapters.HistoryAdapter;
import com.ritshurikt.consentapp.viewmodel.ConsentViewModel;

public class HistoryActivity extends AppCompatActivity {
    private ConsentViewModel viewModel;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            finish();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ConsentViewModel.class);
        viewModel.getConsentHistory().observe(this, records -> {
            if (records != null) {
                adapter.submitList(records);
            }
        });
    }
}