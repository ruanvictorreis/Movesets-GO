package br.ruanvictorreis.movesetgo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Pokemon Model
 * Created by Ruan on 08/10/2016.
 */
public class Pokemon implements Parcelable {

    private String id;

    private Integer number;

    private String name;

    private Integer hpRatio;

    private Integer attackRatio;

    private Integer defenseRatio;

    private Integer maxCpCap;

    private String form;

    private Type typeOne;

    private Type typeTwo;

    private String picture;

    public Pokemon() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getHpRatio() {
        return hpRatio;
    }

    public void setHpRatio(Integer hpRatio) {
        this.hpRatio = hpRatio;
    }

    public Integer getAttackRatio() {
        return attackRatio;
    }

    public void setAttackRatio(Integer attackRatio) {
        this.attackRatio = attackRatio;
    }

    public Integer getDefenseRatio() {
        return defenseRatio;
    }

    public void setDefenseRatio(Integer defenseRatio) {
        this.defenseRatio = defenseRatio;
    }

    public Integer getMaxCpCap() {
        return maxCpCap;
    }

    public void setMaxCpCap(Integer maxCpCap) {
        this.maxCpCap = maxCpCap;
    }

    public Type getTypeOne() {
        return typeOne;
    }

    public void setTypeOne(Type typeOne) {
        this.typeOne = typeOne;
    }

    public Type getTypeTwo() {
        return typeTwo;
    }

    public void setTypeTwo(Type typeTwo) {
        this.typeTwo = typeTwo;
    }

    public Integer getOverall() {
        return getAttackRatio() + getDefenseRatio() + getHpRatio();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
    public String getForm() {
        return form;
    }
     */

    public void setForm(String form) {
        this.form = form;
    }

    public MovesetKind getMovesetKind() {
        return MovesetKind.NORMAL_MOVESET;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon pokemon = (Pokemon) o;

        return (!id.equals(pokemon.id)) && name.equals(pokemon.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public boolean containsType(Type type) {
        return type.equals(getTypeOne()) || (getTypeTwo() != null && type.equals(getTypeTwo()));
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", hpRatio=" + hpRatio +
                ", attackRatio=" + attackRatio +
                ", defenseRatio=" + defenseRatio +
                ", maxCpCap=" + maxCpCap +
                ", form='" + form + '\'' +
                ", typeOne=" + typeOne +
                ", typeTwo=" + typeTwo +
                ", picture='" + picture + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(typeOne, i);
        parcel.writeParcelable(typeTwo, i);

        parcel.writeString(id);
        parcel.writeInt(number);
        parcel.writeString(name);
        parcel.writeInt(hpRatio);
        parcel.writeInt(attackRatio);
        parcel.writeInt(defenseRatio);
        parcel.writeInt(maxCpCap);
        parcel.writeString(form);
        parcel.writeString(picture);
    }

    public Pokemon(Parcel source) {
        this.typeOne = source.readParcelable(Type.class.getClassLoader());
        this.typeTwo = source.readParcelable(Type.class.getClassLoader());

        this.id = source.readString();
        this.number = source.readInt();
        this.name = source.readString();
        this.hpRatio = source.readInt();
        this.attackRatio = source.readInt();
        this.defenseRatio = source.readInt();
        this.maxCpCap = source.readInt();
        this.form = source.readString();
        this.picture = source.readString();
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };
}
