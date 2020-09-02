package com.zhouweixian.instadownloader.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 测试数据
 * Created by WZG on 2016/7/16.
 */
public class SourceApi extends BaseApi {
    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //    接口需要传入的参数 可自定义不同类型
    private String url;


    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     * @param listener
     * @param rxAppCompatActivity
     */
    public SourceApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener,rxAppCompatActivity);
        setShowProgress(true);
        setCancel(true);
//        setCache(true);
        setMethod("AppFiftyToneGraph/videoLink");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }


    @Override
    public Observable getObservable(Retrofit retrofit) {
        SourceService service = retrofit.create(SourceService.class);
        return service.getSource(getUrl());
    }
}