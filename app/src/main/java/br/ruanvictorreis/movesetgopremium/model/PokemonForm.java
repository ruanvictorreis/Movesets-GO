package br.ruanvictorreis.movesetgopremium.model;

public enum PokemonForm {

    NORMAL(1, "(Normal)", "POKEMON_SPECIES"), ALOLA(2, "(Alola)", "POKEMON_ALOLA_SPECIES");

    private int id;

    private String label;

    private String source;

    PokemonForm(int id, String label, String source) {
        this.id = id;
        this.label = label;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }
}
