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
import com.ritshurikt.consentapp.database.ConsentRequest;
import java.text.SimpleDateFormat;

public class HistoryAdapter extends ListAdapter<ConsentRequest, HistoryAdapter.ViewHolder> {

    public HistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ConsentRequest> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ConsentRequest>() {
                @Override
                public boolean areItemsTheSame(@NonNull ConsentRequest oldItem,
                                               @NonNull ConsentRequest newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ConsentRequest oldItem,
                                                  @NonNull ConsentRequest newItem) {
                    return oldItem.getStatus().equals(newItem.getStatus());
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConsentRequest request = getItem(position);
        holder.tvDetails.setText(formatRequestDetails(request));
        holder.tvStatus.setText(request.getStatus());
        holder.tvDate.setText(new SimpleDateFormat("dd MMM yyyy HH:mm")
                .format(request.getTimestamp()));
    }

    private String formatRequestDetails(ConsentRequest request) {
        return "From: " + request.getSenderEmail() + "\nTo: " + request.getReceiverEmail();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetails, tvStatus, tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}