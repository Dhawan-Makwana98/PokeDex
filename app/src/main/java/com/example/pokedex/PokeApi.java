package com.example.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {
    @GET("Biuni/PokemonGO-Pokedex/master/pokedex.json")
    Call<PokemonResponse> getPokemons();
}
