package com.example.pokedex;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private Context context;
    private List<Pokemon> pokemonList;

    public PokemonAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);

        holder.pokemonNameTextView.setText(pokemon.getName());

        Glide.with(context)
                .load(pokemon.getImg())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Image load failed: " + e.getMessage(), e);
                        e.logRootCauses("GlideError");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "Image loaded successfully");
                        return false;
                    }
                })
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .centerCrop())
                .into(holder.pokemonImageView);

        holder.pokemonImageView.setOnClickListener(v -> {
            PokemonDetails detailsFragment = PokemonDetails.newInstance(pokemon);
            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();

            // Ensure fragment container is visible
            View fragmentContainer = ((AppCompatActivity) context).findViewById(R.id.fragment_container);
            if (fragmentContainer != null) {
                fragmentContainer.setVisibility(View.VISIBLE);
            }

            transaction.replace(R.id.fragment_container, detailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    // Method to update the list and notify the adapter
    public void updateList(List<Pokemon> newPokemonList) {
        this.pokemonList = newPokemonList;
        notifyDataSetChanged();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView pokemonNameTextView;
        ImageView pokemonImageView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonNameTextView = itemView.findViewById(R.id.pokemon_name);
            pokemonImageView = itemView.findViewById(R.id.pokemon_image);
        }
    }
}
