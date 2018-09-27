package br.ruanvictorreis.movesetgo.comparators;

import java.util.Comparator;

import br.ruanvictorreis.movesetgo.model.Pokemon;

public class NumberComparator implements Comparator<Pokemon> {

    @Override
    public int compare(Pokemon p1, Pokemon p2) {
        return p1.getId().compareTo(p2.getId());
    }
}
