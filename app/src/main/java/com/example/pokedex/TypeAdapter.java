package com.example.pokedex;// TypeAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    private final Context context;
    private final List<String> types; // Adjust the type if needed

    public TypeAdapter(Context context, List<String> types) {
        this.context = context;
        this.types = types;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String type = types.get(position);
        holder.typeTextView.setText(type);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.type_text_view); // Adjust ID as needed
        }
    }
}
