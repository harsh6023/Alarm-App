package com.jbs.general.repository;

import com.jbs.general.api.ApiClient;
import com.jbs.general.api.ApiService;

public class BaseRepository {

    protected final ApiService apiService;

    public BaseRepository() {
        this.apiService = ApiClient.getRetrofit().create(ApiService.class);
    }
}