package com.example.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Pokemon implements Parcelable {
    private String id;
    private String num;
    private String name;
    private String img;
    private String height;
    private String weight;
    private List<String> type;
    private List<String> weaknesses;
    private List<Evolution> prev_evolution;
    private List<Evolution> next_evolution;

    protected Pokemon(Parcel in) {
        id = in.readString();
        num = in.readString();
        name = in.readString();
        img = in.readString();
        height = in.readString();
        weight = in.readString();
        type = in.createStringArrayList();
        weaknesses = in.createStringArrayList();
        prev_evolution = in.createTypedArrayList(Evolution.CREATOR);
        next_evolution = in.createTypedArrayList(Evolution.CREATOR);
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public String getId() { return id; }
    public String getNum() { return num; }
    public String getName() { return name; }
    public String getImg() { return img; }
    public String getHeight() { return height; }
    public String getWeight() { return weight; }
    public List<String> getType() { return type; }
    public List<String> getWeaknesses() { return weaknesses; }
    public List<Evolution> getPrevEvolution() { return prev_evolution; }
    public List<Evolution> getNextEvolution() { return next_evolution; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(num);
        parcel.writeString(name);
        parcel.writeString(img);
        parcel.writeString(height);
        parcel.writeString(weight);
        parcel.writeStringList(type);
        parcel.writeStringList(weaknesses);
        parcel.writeTypedList(prev_evolution);
        parcel.writeTypedList(next_evolution);
    }
}
