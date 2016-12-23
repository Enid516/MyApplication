package com.example.enid.myapplication.dataS;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by big_love on 2016/12/13.
 */

public class DataModel implements Parcelable{
    private String id;
    private String title;
    private int score;
    public DataModel() {
    }


    protected DataModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        score = in.readInt();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeInt(score);
    }
}
