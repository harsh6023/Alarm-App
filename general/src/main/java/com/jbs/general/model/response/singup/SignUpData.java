package com.jbs.general.model.response.singup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpData implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("login_from")
    @Expose
    private String login_from;
    @SerializedName("subscribed")
    @Expose
    private String subscribed;
    @SerializedName("subscribed_date_time")
    @Expose
    private String subscribed_date_time;
    @SerializedName("subscription_cancel_date_time")
    @Expose
    private String subscription_cancel_date_time;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    protected SignUpData(Parcel in) {
        id = in.readString();
        user_name = in.readString();
        full_name = in.readString();
        email = in.readString();
        password = in.readString();
        login_from = in.readString();
        subscribed = in.readString();
        subscribed_date_time = in.readString();
        subscription_cancel_date_time = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_name);
        dest.writeString(full_name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(login_from);
        dest.writeString(subscribed);
        dest.writeString(subscribed_date_time);
        dest.writeString(subscription_cancel_date_time);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SignUpData> CREATOR = new Creator<SignUpData>() {
        @Override
        public SignUpData createFromParcel(Parcel in) {
            return new SignUpData(in);
        }

        @Override
        public SignUpData[] newArray(int size) {
            return new SignUpData[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin_from() {
        return login_from;
    }

    public String getSubscribed() {
        return subscribed;
    }

    public String getSubscribed_date_time() {
        return subscribed_date_time;
    }

    public String getSubscription_cancel_date_time() {
        return subscription_cancel_date_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
