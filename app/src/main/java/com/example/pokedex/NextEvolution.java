package com.example.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

public class NextEvolution implements Parcelable {
    private String num;
    private String name;

    public NextEvolution(String num, String name) {
        this.num = num;
        this.name = name;
    }

    protected NextEvolution(Parcel in) {
        num = in.readString();
        name = in.readString();
    }

    public static final Creator<NextEvolution> CREATOR = new Creator<NextEvolution>() {
        @Override
        public NextEvolution createFromParcel(Parcel in) {
            return new NextEvolution(in);
        }

        @Override
        public NextEvolution[] newArray(int size) {
            return new NextEvolution[size];
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
