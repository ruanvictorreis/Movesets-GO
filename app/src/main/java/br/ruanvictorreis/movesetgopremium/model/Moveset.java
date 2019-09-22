package br.ruanvictorreis.movesetgopremium.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/**
 * Moveset Model
 * Created by Ruan on 19/02/2017.
 */

public class Moveset implements Parcelable, Comparable<Moveset> {

    private Integer id;

    private Boolean updated;

    private Pokemon pokemon;

    private Double movesetDamage;

    private FastMove fastMove;

    private ChargeMove chargeMove;

    public Moveset() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Double getMovesetDamage() {
        return movesetDamage;
    }

    public void setMovesetDamage(Double movesetDamage) {
        this.movesetDamage = movesetDamage;
    }

    public FastMove getFastMove() {
        return fastMove;
    }

    public void setFastMove(FastMove fastMove) {
        this.fastMove = fastMove;
    }

    public ChargeMove getChargeMove() {
        return chargeMove;
    }

    public void setChargeMove(ChargeMove chargeMove) {
        this.chargeMove = chargeMove;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Moveset moveset = (Moveset) o;

        return getId().equals(moveset.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Moveset{" +
                "id=" + id +
                ", pokemon=" + pokemon +
                ", fastMove=" + fastMove +
                ", chargeMove=" + chargeMove +
                ", movesetDamage=" + movesetDamage +
                ", updated=" + updated +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(pokemon, i);
        parcel.writeParcelable(fastMove, i);
        parcel.writeParcelable(chargeMove, i);

        parcel.writeInt(id);
        parcel.writeInt(updated ? 1 : 0);
    }

    public Moveset(Parcel source) {
        this.pokemon = source.readParcelable(Pokemon.class.getClassLoader());
        this.fastMove = source.readParcelable(FastMove.class.getClassLoader());
        this.chargeMove = source.readParcelable(ChargeMove.class.getClassLoader());

        this.id = source.readInt();
        this.updated = source.readInt() == 1;
    }

    public static final Creator<Moveset> CREATOR
            = new Creator<Moveset>() {
        public Moveset createFromParcel(Parcel in) {
            return new Moveset(in);
        }

        public Moveset[] newArray(int size) {
            return new Moveset[size];
        }
    };

    @Override
    public int compareTo(@NonNull Moveset o) {
        if (this.movesetDamage > o.movesetDamage) {
            return -1;
        } else if (this.movesetDamage < o.movesetDamage) {
            return 1;
        }
        return 0;
    }
}
