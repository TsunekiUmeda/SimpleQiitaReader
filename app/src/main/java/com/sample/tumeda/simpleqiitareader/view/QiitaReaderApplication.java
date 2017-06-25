package com.sample.tumeda.simpleqiitareader.view;

import android.app.Application;

import com.sample.tumeda.simpleqiitareader.model.QiitaApiInterface;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class QiitaReaderApplication extends Application {

    private static final String TAG = QiitaReaderApplication.class.getName();
    private QiitaApiInterface mApiInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        setupAPIClient();
    }

    private void setupAPIClient() {
        final String DEFAULT_URL = "http://qiita.com";
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(DEFAULT_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        mApiInterface = retrofit.create(QiitaApiInterface.class);
    }

    public QiitaApiInterface getQiitaApi() {
        return mApiInterface;
    }
}
