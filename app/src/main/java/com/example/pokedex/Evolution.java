package com.example.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

public class Evolution implements Parcelable {
    private String num;
    private String name;

    protected Evolution(Parcel in) {
        num = in.readString();
        name = in.readString();
    }

    public static final Creator<Evolution> CREATOR = new Creator<Evolution>() {
        @Override
        public Evolution createFromParcel(Parcel in) {
            return new Evolution(in);
        }

        @Override
        public Evolution[] newArray(int size) {
            return new Evolution[size];
        }
    };

    public String getNum() { return num; }
    public String getName() { return name; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(num);
        parcel.writeString(name);
    }
}
