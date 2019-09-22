package br.ruanvictorreis.movesetgopremium.comparators;

import java.util.Comparator;

import br.ruanvictorreis.movesetgopremium.model.Pokemon;

public class NumberComparator implements Comparator<Pokemon> {

    @Override
    public int compare(Pokemon p1, Pokemon p2) {
        return p1.getNumber().compareTo(p2.getNumber());
    }
}
