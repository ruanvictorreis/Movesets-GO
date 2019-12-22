package br.ruanvictorreis.movesetgo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * FastMove Model
 * Updated by Ruan on 15/09/2017.
 */

public class FastMove implements Parcelable {

    private Integer id;

    private String name;

    private String originalName;

    private Double damage;

    private Double duration;

    private Double energy;

    private Type type;

    public FastMove() {
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    public String getPattern(){
        String pattern = getName().trim().toUpperCase();
        pattern = pattern.replace(" ", "_");
        pattern = pattern + "_FAST";
        return pattern;
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
        parcel.writeString(originalName);
        parcel.writeDouble(damage);
        parcel.writeDouble(duration);
        parcel.writeDouble(energy);
    }

    private FastMove(Parcel source) {
        this.type = source.readParcelable(Type.class.getClassLoader());

        this.id = source.readInt();
        this.name = source.readString();
        this.originalName = source.readString();
        this.damage = source.readDouble();
        this.duration = source.readDouble();
        this.energy = source.readDouble();
    }

    public static final Creator<FastMove> CREATOR
            = new Creator<FastMove>() {
        public FastMove createFromParcel(Parcel in) {
            return new FastMove(in);
        }

        public FastMove[] newArray(int size) {
            return new FastMove[size];
        }
    };
}
