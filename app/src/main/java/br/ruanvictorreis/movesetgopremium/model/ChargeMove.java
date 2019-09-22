package br.ruanvictorreis.movesetgopremium.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChargeMove Model
 * Updated by Ruan on 15/09/2017.
 */

public class ChargeMove implements Parcelable {

    private Integer id;

    private String name;

    private Double damage;

    private Double duration;

    private Double critical;

    private Double spentEnergy;

    private Type type;

    public ChargeMove() {
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

    public Double getCritical() {
        return critical;
    }

    public void setCritical(Double critical) {
        this.critical = critical;
    }

    public Double getSpentEnergy() {
        return spentEnergy;
    }

    public void setSpentEnergy(Double spentEnergy) {
        this.spentEnergy = spentEnergy;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPattern() {
        String pattern = getName().trim().toUpperCase();
        pattern = pattern.replace(" ", "_");
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
        parcel.writeDouble(damage);
        parcel.writeDouble(duration);
        parcel.writeDouble(critical);
        parcel.writeDouble(spentEnergy);
    }

    private ChargeMove(Parcel source) {
        this.type = source.readParcelable(Type.class.getClassLoader());

        this.id = source.readInt();
        this.name = source.readString();
        this.damage = source.readDouble();
        this.duration = source.readDouble();
        this.critical = source.readDouble();
        this.spentEnergy = source.readDouble();
    }

    public static final Creator<ChargeMove> CREATOR
            = new Creator<ChargeMove>() {
        public ChargeMove createFromParcel(Parcel in) {
            return new ChargeMove(in);
        }

        public ChargeMove[] newArray(int size) {
            return new ChargeMove[size];
        }
    };
}
