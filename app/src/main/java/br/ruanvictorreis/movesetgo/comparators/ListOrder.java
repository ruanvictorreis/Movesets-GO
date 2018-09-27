package br.ruanvictorreis.movesetgo.comparators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import br.ruanvictorreis.movesetgo.model.Pokemon;

public enum ListOrder {

    MAX_CP(new CpComparator(), 0), NAME(new NameComparator(), 1), NUMBER(new NumberComparator(), 2);

    private Comparator<Pokemon> comparator;

    private Integer index;

    ListOrder(Comparator<Pokemon> comparator, Integer index) {
        this.comparator = comparator;
        this.index = index;
    }

    public Comparator<Pokemon> getComparator() {
        return comparator;
    }

    public Integer getIndex() {
        return index;
    }

    public static List<ListOrder> asList() {
        return new ArrayList<>(Arrays.asList(ListOrder.values()));
    }
}
