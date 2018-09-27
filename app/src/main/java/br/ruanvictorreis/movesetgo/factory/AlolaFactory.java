package br.ruanvictorreis.movesetgo.factory;

import br.ruanvictorreis.movesetgo.model.Pokemon;
import br.ruanvictorreis.movesetgo.model.PokemonAlola;

public class AlolaFactory implements PokemonFactory {

    @Override
    public Pokemon createInstance() {
        return new PokemonAlola();
    }
}
