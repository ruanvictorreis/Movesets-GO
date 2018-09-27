package br.ruanvictorreis.movesetgo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import br.ruanvictorreis.movesetgo.R;

/**
 * Type Model
 * Created by Ruan on 29/11/2016.
 */

public class Type implements Parcelable {

    private Integer id;

    private String name;

    private List<Type> strengthChart, weaknessChart;

    private int[] typeIconsResource = {R.mipmap.ic_bug, R.mipmap.ic_dark, R.mipmap.ic_dragon,
            R.mipmap.ic_electric, R.mipmap.ic_fairy, R.mipmap.ic_fighting, R.mipmap.ic_fire,
            R.mipmap.ic_flying, R.mipmap.ic_ghost, R.mipmap.ic_grass, R.mipmap.ic_ground,
            R.mipmap.ic_ice, R.mipmap.ic_normal, R.mipmap.ic_poison, R.mipmap.ic_psychic,
            R.mipmap.ic_rock, R.mipmap.ic_steel, R.mipmap.ic_water};

    public Type() {
        super();
    }

    public Type(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.strengthChart = new ArrayList<>();
        this.weaknessChart = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMipmapResource() {
        return typeIconsResource[getId() - 1];
    }

    public List<Type> getStrengthChart() {
        return strengthChart;
    }

    public void setStrengthChart(List<Type> strengthChart) {
        this.strengthChart = strengthChart;
    }

    public List<Type> getWeaknessChart() {
        return weaknessChart;
    }

    public void setWeaknessChart(List<Type> weaknessChart) {
        this.weaknessChart = weaknessChart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (!getId().equals(type.getId())) return false;
        return getName().equals(type.getName());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public Type(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
    }

    public static final Parcelable.Creator<Type> CREATOR
            = new Parcelable.Creator<Type>() {
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        public Type[] newArray(int size) {
            return new Type[size];
        }
    };
}
