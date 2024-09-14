package com.example.pokedex;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    private EditText searchBar;
    private List<Pokemon> pokemonList = new ArrayList<>();
    private static final String SCROLL_STATE_KEY = "scroll_state";
    private Bundle recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.gridView);
        searchBar = findViewById(R.id.searchBar);

        // Initialize GridLayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 columns for grid view
        recyclerView.setLayoutManager(layoutManager);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.topColor));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeApi api = retrofit.create(PokeApi.class);
        Call<PokemonResponse> call = api.getPokemons();

        if (savedInstanceState == null) {
            Pokemon pokemon = getIntent().getParcelableExtra("pokemon");
            if (pokemon != null) {
                loadPokemonDetailsFragment(pokemon);
            }
        }

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemonList = response.body().getPokemon();
                    if (pokemonList != null && !pokemonList.isEmpty()) {
                        pokemonAdapter = new PokemonAdapter(MainActivity.this, pokemonList);
                        recyclerView.setAdapter(pokemonAdapter);

                        // Restore the scroll state
                        if (savedInstanceState != null) {
                            recyclerViewState = savedInstanceState.getBundle(SCROLL_STATE_KEY);
                            if (recyclerViewState != null) {
                                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                            }
                        }

                        // Setup search functionality
                        searchBar.addTextChangedListener(new android.text.TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                filterPokemon(s.toString());
                            }

                            @Override
                            public void afterTextChanged(android.text.Editable s) {}
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        // Fragment Manager to handle fragment changes and search bar visibility
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment == null) {
                searchBar.setVisibility(View.VISIBLE);
            } else {
                searchBar.setVisibility(View.GONE);
            }
        });
    }

    private void filterPokemon(String query) {
        List<Pokemon> filteredList = new ArrayList<>();
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(pokemon);
            }
        }
        pokemonAdapter.updateList(filteredList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the scroll state
        Bundle state = new Bundle();
        state.putParcelable("recycler_state", recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putBundle(SCROLL_STATE_KEY, state);
    }
    private void loadPokemonDetailsFragment(Pokemon pokemon) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PokemonDetails pokemonDetails = PokemonDetails.newInstance(pokemon);
        fragmentTransaction.replace(R.id.fragment_container, pokemonDetails);
        fragmentTransaction.addToBackStack(null); // Add to back stack
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof PokemonDetails) {
            // Close the fragment and reload the MainActivity
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            searchBar.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed(); // Handle default back press behavior
        }
    }
}
