package br.ruanvictorreis.movesetgo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class PokemonAlola extends Pokemon {

    public PokemonAlola() {
        super();
    }

    @Override
    public String getName() {
        return super.getName() + " " + getForm().getLabel();
    }

    @Override
    public PokemonForm getForm() {
        return PokemonForm.ALOLA;
    }

    @Override
    public MovesetKind getMovesetKind() {
        return MovesetKind.ALOLA_MOVESET;
    }

    @Override
    public String getSuggestionPictureLink() {
        return "https://db.pokemongohub.net/images/official/full/" + getFormattedId() + "_f2.png";
    }

    private String getFormattedId() {
        return String.format(Locale.US, "%03d", getId());
    }

    private PokemonAlola(Parcel source) {
        this.typeOne = source.readParcelable(Type.class.getClassLoader());
        this.typeTwo = source.readParcelable(Type.class.getClassLoader());

        this.id = source.readInt();
        this.name = source.readString();
        this.hpRatio = source.readInt();
        this.attackRatio = source.readInt();
        this.defenseRatio = source.readInt();
        this.maxCpCap = source.readInt();
    }

    public static final Parcelable.Creator<PokemonAlola> CREATOR = new Parcelable.Creator<PokemonAlola>() {
        public PokemonAlola createFromParcel(Parcel in) {
            return new PokemonAlola(in);
        }

        public PokemonAlola[] newArray(int size) {
            return new PokemonAlola[size];
        }
    };
}

