package com.ritshurikt.consentapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ritshurikt.consentapp.R;
import com.ritshurikt.consentapp.database.ConsentRecord;
import java.text.SimpleDateFormat;

public class HistoryAdapter extends ListAdapter<ConsentRecord, HistoryAdapter.ViewHolder> {
    public HistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ConsentRecord> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ConsentRecord>() {
                @Override
                public boolean areItemsTheSame(@NonNull ConsentRecord oldItem,
                                               @NonNull ConsentRecord newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ConsentRecord oldItem,
                                                  @NonNull ConsentRecord newItem) {
                    return oldItem.getStatus().equals(newItem.getStatus());
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConsentRecord record = getItem(position);
        holder.tvStatus.setText(record.getStatus());
        holder.tvDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm")
                .format(record.getTimestamp()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus, tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}