package br.ruanvictorreis.movesetgo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * BasicMove Model
 * Updated by Ruan on 15/09/2017.
 */

public class BasicMove implements Parcelable {

    private Integer id;

    private String name;

    private Double damage;

    private Double duration;

    private Double energy;

    private Type type;

    public BasicMove() {
        super();
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

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(type, i);

        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(damage);
        parcel.writeDouble(duration);
        parcel.writeDouble(energy);
    }

    private BasicMove(Parcel source) {
        this.type = source.readParcelable(Type.class.getClassLoader());

        this.id = source.readInt();
        this.name = source.readString();
        this.damage = source.readDouble();
        this.duration = source.readDouble();
        this.energy = source.readDouble();
    }

    public static final Creator<BasicMove> CREATOR
            = new Creator<BasicMove>() {
        public BasicMove createFromParcel(Parcel in) {
            return new BasicMove(in);
        }

        public BasicMove[] newArray(int size) {
            return new BasicMove[size];
        }
    };
}
