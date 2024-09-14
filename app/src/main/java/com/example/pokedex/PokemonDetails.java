package com.example.pokedex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.Map;

public class PokemonDetails extends Fragment {

    private static final String ARG_POKEMON = "pokemon";
    private MediaPlayer mediaPlayer;
    private Map<String, Integer> audioMap;

    public static PokemonDetails newInstance(Pokemon pokemon) {
        PokemonDetails fragment = new PokemonDetails();
        Bundle args = new Bundle();
        args.putParcelable(ARG_POKEMON, pokemon);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        TextView pokemonName = view.findViewById(R.id.pokemon_name);
        ImageView pokemonImage = view.findViewById(R.id.pokemon_image);
        ImageView backbtn = view.findViewById(R.id.backbtn);
        TextView pokemonHeight = view.findViewById(R.id.height);
        TextView pokemonWeight = view.findViewById(R.id.weight);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView nextTxt = view.findViewById(R.id.nextTxt);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView prevTxt = view.findViewById(R.id.prevTxt);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView weaknesstxt = view.findViewById(R.id.weaknesstxt);

        RecyclerView recyclerType = view.findViewById(R.id.recycler_type);
        RecyclerView recyclerWeakness = view.findViewById(R.id.recycler_weakness);
        RecyclerView recyclerPrevEvolution = view.findViewById(R.id.recycler_prev_evolution);
        RecyclerView recyclerNextEvolution = view.findViewById(R.id.recycler_next_evolution);

        recyclerType.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerWeakness.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerPrevEvolution.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNextEvolution.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        initializeAudioMap(); // Initialize the audio map

        if (getArguments() != null) {
            Pokemon pokemon = getArguments().getParcelable(ARG_POKEMON);

            if (pokemon != null) {
                pokemonName.setText(pokemon.getName());
                pokemonHeight.setText("Height: " + pokemon.getHeight());
                pokemonWeight.setText("Weight: " + pokemon.getWeight());

                Glide.with(this)
                        .load(pokemon.getImg())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("Glide", "Image load failed", e);
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
                        .into(pokemonImage);

                pokemonImage.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Clicked: " + pokemon.getName(), Toast.LENGTH_SHORT).show();
                    playPokemonAudio(pokemon.getName().toLowerCase().replace(" ", "_")); // Play the corresponding audio
                });

                // Set up TypeAdapter and WeaknessAdapter
                TypeAdapter typeAdapter = new TypeAdapter(requireContext(), pokemon.getType());
                recyclerType.setAdapter(typeAdapter);

                if (pokemon.getWeaknesses() != null && !pokemon.getWeaknesses().isEmpty()) {
                    Log.d("PokemonDetails", "Weaknesses: " + pokemon.getWeaknesses().toString());
                    WeaknessAdapter weaknessAdapter = new WeaknessAdapter(requireContext(), pokemon.getWeaknesses());
                    recyclerWeakness.setAdapter(weaknessAdapter);
                    recyclerWeakness.setVisibility(View.VISIBLE);
                    weaknesstxt.setVisibility(View.VISIBLE);
                } else {
                    Log.d("PokemonDetails", "No weaknesses found");
                    recyclerWeakness.setVisibility(View.GONE);
                    weaknesstxt.setVisibility(View.GONE);
                }

                if (pokemon.getPrevEvolution() == null || pokemon.getPrevEvolution().isEmpty()) {
                    recyclerPrevEvolution.setVisibility(View.GONE); // Hide RecyclerView if empty
                    prevTxt.setVisibility(View.GONE);
                } else {
                    EvolutionAdapter prevEvolutionAdapter = new EvolutionAdapter(requireContext(), pokemon.getPrevEvolution());
                    recyclerPrevEvolution.setAdapter(prevEvolutionAdapter);
                    recyclerPrevEvolution.setVisibility(View.VISIBLE); // Show RecyclerView if not empty
                    prevTxt.setVisibility(View.VISIBLE);
                }

                if (pokemon.getNextEvolution() == null || pokemon.getNextEvolution().isEmpty()) {
                    recyclerNextEvolution.setVisibility(View.GONE); // Hide RecyclerView if empty
                    nextTxt.setVisibility(View.GONE);
                } else {
                    EvolutionAdapter nextEvolutionAdapter = new EvolutionAdapter(requireContext(), pokemon.getNextEvolution());
                    recyclerNextEvolution.setAdapter(nextEvolutionAdapter);
                    recyclerNextEvolution.setVisibility(View.VISIBLE); // Show RecyclerView if not empty
                    nextTxt.setVisibility(View.VISIBLE);
                }
            }
        }

        backbtn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }


    private void initializeAudioMap() {
        audioMap = new HashMap<>();
        audioMap.put("bulbasaur", R.raw.bulbasaur);
        audioMap.put("ivysaur", R.raw.ivysaur);
        audioMap.put("venusaur", R.raw.venusaur);
        audioMap.put("charmander", R.raw.charmander);
        audioMap.put("charmeleon", R.raw.charmeleon);
        audioMap.put("charizard", R.raw.charizard);
        audioMap.put("squirtle", R.raw.squirtel);
        audioMap.put("wartortle", R.raw.wartortle);
        audioMap.put("blastoise", R.raw.blastoise);
        audioMap.put("caterpie", R.raw.caterpie);
        audioMap.put("metapod", R.raw.metapod);
        audioMap.put("butterfree", R.raw.butterfree);
        audioMap.put("weedle", R.raw.weedle);
        audioMap.put("kakuna", R.raw.kakuna);
        audioMap.put("beedrill", R.raw.beedrill);
        audioMap.put("pidgey", R.raw.pidgey);
        audioMap.put("pidgeotto", R.raw.pidgeotto);
        audioMap.put("pidgeot", R.raw.pidgeot);
        audioMap.put("rattata", R.raw.rattata);
        audioMap.put("raticate", R.raw.raticate);
        audioMap.put("spearow", R.raw.spearow);
        audioMap.put("fearow", R.raw.fearow);
        audioMap.put("ekans", R.raw.ekans);
        audioMap.put("arbok", R.raw.arbok);
        audioMap.put("pikachu", R.raw.pikachu);
        audioMap.put("raichu", R.raw.raichu);
        audioMap.put("sandshrew", R.raw.sandshrew);
        audioMap.put("sandslash", R.raw.sandslash);
        audioMap.put("nidoran♀", R.raw.nidoran);
        audioMap.put("nidorina", R.raw.nidorina);
        audioMap.put("nidoqueen", R.raw.nidoqueen);
        audioMap.put("nidoran♂", R.raw.nidoran);
        audioMap.put("nidorino", R.raw.nidorino);
        audioMap.put("nidoking", R.raw.nidoking);
        audioMap.put("clefairy", R.raw.clefairy);
        audioMap.put("clefable", R.raw.clefable);
        audioMap.put("vulpix", R.raw.vulpix);
        audioMap.put("ninetales", R.raw.ninetales);
        audioMap.put("jigglypuff", R.raw.jigglypuff);
        audioMap.put("wigglytuff", R.raw.wigglypuff);
        audioMap.put("zubat", R.raw.zubat);
        audioMap.put("golbat", R.raw.golbat);
        audioMap.put("oddish", R.raw.oddish);
        audioMap.put("gloom", R.raw.gloom);
        audioMap.put("vileplume", R.raw.vileplume);
        audioMap.put("paras", R.raw.paras);
        audioMap.put("parasect", R.raw.parasect);
        audioMap.put("venonat", R.raw.venonat);
        audioMap.put("venomoth", R.raw.venomoth);
        audioMap.put("diglett", R.raw.diglett);
        audioMap.put("dugtrio", R.raw.dugtrio);
        audioMap.put("meowth", R.raw.meowth);
        audioMap.put("persian", R.raw.persian);
        audioMap.put("psyduck", R.raw.psyduck);
        audioMap.put("Golduck", R.raw.golduck);
        audioMap.put("mankey", R.raw.mankey);
        audioMap.put("primeape", R.raw.primeape);
        audioMap.put("growlithe", R.raw.growlithe);
        audioMap.put("arcanine", R.raw.arcanine);
        audioMap.put("poliwag", R.raw.poliwag);
        audioMap.put("poliwhirl", R.raw.poliwhirl);
        audioMap.put("poliwrath", R.raw.poliwrath);
        audioMap.put("abra", R.raw.abra);
        audioMap.put("kadabra", R.raw.kadabra);
        audioMap.put("alakazam", R.raw.alakazam);
        audioMap.put("machop", R.raw.machop);
        audioMap.put("machoke", R.raw.machoke);
        audioMap.put("machamp", R.raw.machamp);
        audioMap.put("bellsprout", R.raw.bellsprout);
        audioMap.put("weepinbell", R.raw.weepinbell);
        audioMap.put("victreebel", R.raw.victreebel);
        audioMap.put("tentacool", R.raw.tentacool);
        audioMap.put("tentacruel", R.raw.tentacruel);
        audioMap.put("geodude", R.raw.geodude);
        audioMap.put("graveler", R.raw.graveler);
        audioMap.put("golem", R.raw.golem);
        audioMap.put("ponyta", R.raw.ponyta);
        audioMap.put("rapidash", R.raw.rapidash);
        audioMap.put("slowpoke", R.raw.slowpoke);
        audioMap.put("slowbro", R.raw.slowbro);
        audioMap.put("magnemite", R.raw.magnemite);
        audioMap.put("magneton", R.raw.magneton);
        audioMap.put("farfetch'd", R.raw.farfetchd);
        audioMap.put("doduo", R.raw.doduo);
        audioMap.put("dodrio", R.raw.dodrio);
        audioMap.put("seel", R.raw.seel);
        audioMap.put("dewgong", R.raw.dewgong);
        audioMap.put("grimer", R.raw.grimer);
        audioMap.put("muk", R.raw.muk);
        audioMap.put("shellder", R.raw.shellder);
        audioMap.put("cloyster", R.raw.cloyster);
        audioMap.put("gastly", R.raw.gastly);
        audioMap.put("haunter", R.raw.haunter);
        audioMap.put("gengar", R.raw.gengar);
        audioMap.put("onix", R.raw.onix);
        audioMap.put("drowzee", R.raw.drowzee);
        audioMap.put("hypno", R.raw.hypno);
        audioMap.put("krabby", R.raw.krabby);
        audioMap.put("kingler", R.raw.kingler);
        audioMap.put("voltorb", R.raw.voltorb);
        audioMap.put("electrode", R.raw.electrode);
        audioMap.put("exeggcute", R.raw.exeggcute);
        audioMap.put("exeggutor", R.raw.exeggutor);
        //audioMap.put("todo", R.raw.todo);
        //audioMap.put("vickytree", R.raw.vickytree);
        //audioMap.put("sea", R.raw.sea);
        //audioMap.put("leech", R.raw.leech);
        //audioMap.put("hypno", R.raw.hypno);
        //audioMap.put("dugtrio", R.raw.dugtrio);
        //audioMap.put("mankey", R.raw.mankey);
        //audioMap.put("winged", R.raw.winged);
        //audioMap.put("mewtwo", R.raw.mewtwo);
        //audioMap.put("mew", R.raw.mew);
    }

    private void playPokemonAudio(String pokemonName) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        Integer audioResId = audioMap.get(pokemonName);
        if (audioResId != null) {
            mediaPlayer = MediaPlayer.create(getContext(), audioResId);
            mediaPlayer.start();
        } else {
            Toast.makeText(getContext(), "Audio not found for: " + pokemonName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}