package com.ritshurikt.consentapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.ritshurikt.consentapp.R;
import com.ritshurikt.consentapp.database.ConsentRequest;
import java.text.SimpleDateFormat;

public class PendingRequestsAdapter extends ListAdapter<ConsentRequest, PendingRequestsAdapter.ViewHolder> {
    private OnResponseListener responseListener;

    public interface OnResponseListener {
        void onResponse(ConsentRequest request, boolean accepted);
    }

    public PendingRequestsAdapter() {
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
                .inflate(R.layout.item_pending_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConsentRequest request = getItem(position);
        holder.tvSenderEmail.setText(request.getSenderEmail());
        holder.tvTimestamp.setText(new SimpleDateFormat("dd MMM yyyy HH:mm")
                .format(request.getTimestamp()));

        holder.btnAccept.setOnClickListener(v -> {
            if (responseListener != null) {
                responseListener.onResponse(request, true);
            }
        });

        holder.btnDeny.setOnClickListener(v -> {
            if (responseListener != null) {
                responseListener.onResponse(request, false);
            }
        });
    }

    public void setOnResponseListener(OnResponseListener listener) {
        this.responseListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSenderEmail, tvTimestamp;
        MaterialButton btnAccept, btnDeny;

        ViewHolder(View itemView) {
            super(itemView);
            tvSenderEmail = itemView.findViewById(R.id.tvSenderEmail);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDeny = itemView.findViewById(R.id.btnDeny);
        }
    }
}