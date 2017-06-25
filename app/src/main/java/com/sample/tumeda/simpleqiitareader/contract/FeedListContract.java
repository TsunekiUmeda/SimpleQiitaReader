package com.sample.tumeda.simpleqiitareader.contract;

import com.sample.tumeda.simpleqiitareader.data.rss.Entry;
import com.sample.tumeda.simpleqiitareader.data.rss.Feed;


public interface FeedListContract {

    interface View {
        void showFeed(Feed feed);

        void showError();

        void showDetail(String fullRepositoryName);
    }

    interface UserActions {
        void loadFeed(String tag);

        void selectFeedItem(Entry item);
    }

}


