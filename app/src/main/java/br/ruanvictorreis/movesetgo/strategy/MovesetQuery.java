package br.ruanvictorreis.movesetgo.strategy;

import java.util.List;

import br.ruanvictorreis.movesetgo.model.Moveset;
import br.ruanvictorreis.movesetgo.model.Pokemon;

/**
 * Interface to manage queries in Movesets
 * Created by Ruan on 17/09/2017.
 */

public interface MovesetQuery {

    List<Moveset> executeSelectQuery(Pokemon pokemon);
}
