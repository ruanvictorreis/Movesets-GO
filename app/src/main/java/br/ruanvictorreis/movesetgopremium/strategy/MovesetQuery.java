package br.ruanvictorreis.movesetgopremium.strategy;

import java.util.List;

import br.ruanvictorreis.movesetgopremium.model.Moveset;
import br.ruanvictorreis.movesetgopremium.model.Pokemon;

/**
 * Interface to manage queries in Movesets
 * Created by Ruan on 17/09/2017.
 */

public interface MovesetQuery {

    List<Moveset> executeSelectQuery(Pokemon pokemon);
}
