package com.jbs.general.model.response.commentary;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class CommentaryData implements Parcelable {
    @SerializedName("2 Inning")
    @Expose
    private Object getCommentary2List;
    @SerializedName("1 Inning")
    @Expose
    private Object getCommentary1List;

    protected CommentaryData(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentaryData> CREATOR = new Creator<CommentaryData>() {
        @Override
        public CommentaryData createFromParcel(Parcel in) {
            return new CommentaryData(in);
        }

        @Override
        public CommentaryData[] newArray(int size) {
            return new CommentaryData[size];
        }
    };

    public Object getGetCommentary2List() {
        return getCommentary2List;
    }

    public Object getGetCommentary1List() {
        return getCommentary1List;
    }
}
