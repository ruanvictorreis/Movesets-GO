package br.ruanvictorreis.movesetgo.strategy;

import java.util.List;

import br.ruanvictorreis.movesetgo.activities.TabFragmentMoveset;
import br.ruanvictorreis.movesetgo.database.dao.MovesetsDAO;
import br.ruanvictorreis.movesetgo.model.Moveset;
import br.ruanvictorreis.movesetgo.model.Pokemon;

public class AvailableMovesets implements MovesetQuery {

    private TabFragmentMoveset fragment;

    public AvailableMovesets(TabFragmentMoveset fragment) {
        this.fragment = fragment;
    }

    @Override
    public List<Moveset> executeSelectQuery(Pokemon pokemon) {
        MovesetsDAO movesetsDAO = new MovesetsDAO(fragment.getActivity());
        return movesetsDAO.selectMovesetsAvailable(pokemon);
    }
}
