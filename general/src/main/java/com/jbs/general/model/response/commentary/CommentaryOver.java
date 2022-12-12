package com.jbs.general.model.response.commentary;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentaryOver implements Parcelable {
    @SerializedName("commentary_id")
    @Expose
    private String commentary_id;
    @SerializedName("inning")
    @Expose
    private String inning;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private CommentaryOverData overData;

    protected CommentaryOver(Parcel in) {
        commentary_id = in.readString();
        inning = in.readString();
        type = in.readString();
    }

    public static final Creator<CommentaryOver> CREATOR = new Creator<CommentaryOver>() {
        @Override
        public CommentaryOver createFromParcel(Parcel in) {
            return new CommentaryOver(in);
        }

        @Override
        public CommentaryOver[] newArray(int size) {
            return new CommentaryOver[size];
        }
    };

    public String getCommentary_id() {
        return commentary_id;
    }

    public String getInning() {
        return inning;
    }

    public String getType() {
        return type;
    }

    public CommentaryOverData getOverData() {
        return overData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(commentary_id);
        parcel.writeString(inning);
        parcel.writeString(type);
    }
}
