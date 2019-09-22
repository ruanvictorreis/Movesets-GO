package br.ruanvictorreis.movesetgopremium.model;

public enum MovesetKind {

    NORMAL_MOVESET("POKEMON_MOVESETS_2"), ALOLA_MOVESET("POKEMON_ALOLA_MOVESET");

    private String source;

    MovesetKind(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
