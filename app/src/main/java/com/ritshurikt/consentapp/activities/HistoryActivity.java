package com.ritshurikt.consentapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

        RecyclerView recyclerView = findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ConsentViewModel.class);
        viewModel.getAllUserRequests().observe(this, requests -> {
            if (requests != null) {
                adapter.submitList(requests);
            }
        });
    }
}