package com.jbs.general.model.response.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseObjectResponse<T> extends BaseResponse {

    //change key if needed
    @SerializedName("data")
    @Expose
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseObjectResponse{" +
                "data=" + data +
                '}';
    }
}
