package com.example.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

public class PrevEvolution implements Parcelable {
    private String num;
    private String name;

    public PrevEvolution(String num, String name) {
        this.num = num;
        this.name = name;
    }

    protected PrevEvolution(Parcel in) {
        num = in.readString();
        name = in.readString();
    }

    public static final Creator<PrevEvolution> CREATOR = new Creator<PrevEvolution>() {
        @Override
        public PrevEvolution createFromParcel(Parcel in) {
            return new PrevEvolution(in);
        }

        @Override
        public PrevEvolution[] newArray(int size) {
            return new PrevEvolution[size];
        }
    };

    public String getNum() { return num; }
    public void setNum(String num) { this.num = num; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(num);
        dest.writeString(name);
    }
}
