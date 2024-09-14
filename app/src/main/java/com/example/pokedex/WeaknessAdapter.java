package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeaknessAdapter extends RecyclerView.Adapter<WeaknessAdapter.ViewHolder> {
    private final Context context;
    private final List<String> weaknesses; // Assuming a list of weaknesses

    public WeaknessAdapter(Context context, List<String> weaknesses) {
        this.context = context;
        this.weaknesses = weaknesses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weakness, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String weakness = weaknesses.get(position);
        holder.weaknessTextView.setText(weakness);
    }

    @Override
    public int getItemCount() {
        return weaknesses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView weaknessTextView;

        ViewHolder(View itemView) {
            super(itemView);
            weaknessTextView = itemView.findViewById(R.id.weakness_text_view);
        }
    }
}
