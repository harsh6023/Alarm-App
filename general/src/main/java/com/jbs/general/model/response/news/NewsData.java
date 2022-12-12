package com.jbs.general.model.response.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsData implements Parcelable {
    @SerializedName("news_id")
    @Expose
    private String news_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("match_date")
    @Expose
    private String match_date;
    @SerializedName("pub_date")
    @Expose
    private String pub_date;
    @SerializedName("content")
    @Expose
    private List<String> contentList = new ArrayList<>();

    protected NewsData(Parcel in) {
        news_id = in.readString();
        title = in.readString();
        description = in.readString();
        image = in.readString();
        match_date = in.readString();
        pub_date = in.readString();
        contentList = in.createStringArrayList();
    }

    public static final Creator<NewsData> CREATOR = new Creator<NewsData>() {
        @Override
        public NewsData createFromParcel(Parcel in) {
            return new NewsData(in);
        }

        @Override
        public NewsData[] newArray(int size) {
            return new NewsData[size];
        }
    };

    public String getNews_id() {
        return news_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getMatch_date() {
        return match_date;
    }

    public String getPub_date() {
        return pub_date;
    }

    public List<String> getContentList() {
        return contentList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(news_id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(image);
        parcel.writeString(match_date);
        parcel.writeString(pub_date);
        parcel.writeStringList(contentList);
    }
}
