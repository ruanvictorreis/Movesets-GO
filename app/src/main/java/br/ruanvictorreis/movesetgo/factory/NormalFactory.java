package br.ruanvictorreis.movesetgo.factory;

import br.ruanvictorreis.movesetgo.model.Pokemon;

public class NormalFactory implements PokemonFactory {

    @Override
    public Pokemon createInstance() {
        return new Pokemon();
    }
}
