package br.ruanvictorreis.movesetgopremium.comparators;

import java.util.Comparator;

import br.ruanvictorreis.movesetgopremium.model.Pokemon;

public class CpComparator implements Comparator<Pokemon> {

    @Override
    public int compare(Pokemon p1, Pokemon p2) {
        return p2.getMaxCpCap().compareTo(p1.getMaxCpCap());
    }
}
