package com.zhouweixian.instadownloader.api;

import com.zhouweixian.instadownloader.models.SourceModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SourceService {
    @GET("https://dl.kangspace.org/ins-get")
    Observable<SourceModel> getSource(@Query("url") String url);
}
