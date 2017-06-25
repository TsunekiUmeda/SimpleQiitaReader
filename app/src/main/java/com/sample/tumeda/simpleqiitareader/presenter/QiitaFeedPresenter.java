package com.sample.tumeda.simpleqiitareader.presenter;

import android.util.Log;

import com.sample.tumeda.simpleqiitareader.data.rss.Entry;
import com.sample.tumeda.simpleqiitareader.model.QiitaApiInterface;
import com.sample.tumeda.simpleqiitareader.contract.FeedListContract;
import com.sample.tumeda.simpleqiitareader.data.rss.Feed;
import com.sample.tumeda.simpleqiitareader.view.QiitaFeedListActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QiitaFeedPresenter implements FeedListContract.UserActions {

    private static final String TAG = QiitaFeedPresenter.class.getName();
    private final FeedListContract.View feedListView;
    private final QiitaApiInterface qiitaApiInterface;

    public QiitaFeedPresenter(FeedListContract.View feedListView, QiitaApiInterface qiitaApiInterface) {
        this.feedListView = feedListView;
        this.qiitaApiInterface = qiitaApiInterface;
    }

    @Override
    public void loadFeed(String tag) {
        loadQiitaFeed(tag);
    }

    @Override
    public void selectFeedItem(Entry item) {
        feedListView.showDetail(item.getLink().getHref());
    }

    private void loadQiitaFeed(String tag) {
        Log.d(TAG, tag);
        Observable<Feed> observable;
        if (tag.isEmpty() || tag.equalsIgnoreCase(QiitaFeedListActivity.POPULAR_FEED_TAG)) {
            observable = qiitaApiInterface.getPopularFeed();
        } else {
            observable = qiitaApiInterface.getFeedWithTag(tag);
        }
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Feed>() {
            @Override
            public void onNext(Feed feed) {
                Log.d(TAG, "onNext " + feed.getTitle());

                feedListView.showFeed(feed);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError " + e.getMessage());
                feedListView.showError();
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted ");
            }
        });
    }
}