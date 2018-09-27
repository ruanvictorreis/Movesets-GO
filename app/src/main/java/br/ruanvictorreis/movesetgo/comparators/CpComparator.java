package br.ruanvictorreis.movesetgo.comparators;

import java.util.Comparator;

import br.ruanvictorreis.movesetgo.model.Pokemon;

public class CpComparator implements Comparator<Pokemon> {

    @Override
    public int compare(Pokemon p1, Pokemon p2) {
        return p2.getMaxCpCap().compareTo(p1.getMaxCpCap());
    }
}
