package br.ruanvictorreis.movesetgopremium.strategy;

import java.util.List;

import br.ruanvictorreis.movesetgopremium.activities.TabFragmentMoveset;
import br.ruanvictorreis.movesetgopremium.database.dao.MovesetsDAO;
import br.ruanvictorreis.movesetgopremium.model.Moveset;
import br.ruanvictorreis.movesetgopremium.model.Pokemon;

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
