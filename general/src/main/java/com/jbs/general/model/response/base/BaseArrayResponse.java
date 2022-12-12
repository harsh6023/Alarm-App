package com.jbs.general.model.response.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseArrayResponse<T> extends BaseResponse {

    //change key if needed
    @SerializedName("data")
    @Expose
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseArrayResponse{" +
                "data=" + data +
                '}';
    }
}
