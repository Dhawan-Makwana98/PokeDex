package com.example.pokedex;

import java.util.List;

public class PokemonResponse {
    private List<Pokemon> pokemon;

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
