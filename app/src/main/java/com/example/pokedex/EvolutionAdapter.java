package com.example.pokedex;// EvolutionAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.Evolution;

import java.util.Collections;
import java.util.List;

public class EvolutionAdapter extends RecyclerView.Adapter<EvolutionAdapter.ViewHolder> {
    private final Context context;
    private final List<Evolution> evolutions; // Replace Evolution with your model class

    public EvolutionAdapter(Context context, List<Evolution> evolutions) {
        this.context = context;
        this.evolutions = evolutions != null ? evolutions : Collections.emptyList(); // Handle null list
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_evolution, parent, false); // Adjust layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evolution evolution = evolutions.get(position); // Replace with actual model
        holder.evolutionTextView.setText(evolution.getName()); // Adjust according to your model
    }

    @Override
    public int getItemCount() {
        return evolutions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView evolutionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            evolutionTextView = itemView.findViewById(R.id.evolution_text_view); // Adjust ID
        }
    }
}
