package com.sample.tumeda.simpleqiitareader.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.sample.tumeda.simpleqiitareader.R;
import com.sample.tumeda.simpleqiitareader.contract.FeedListContract;
import com.sample.tumeda.simpleqiitareader.data.rss.Entry;
import com.sample.tumeda.simpleqiitareader.data.rss.Feed;
import com.sample.tumeda.simpleqiitareader.model.QiitaApiInterface;
import com.sample.tumeda.simpleqiitareader.presenter.QiitaFeedPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QiitaFeedListActivity extends AppCompatActivity
        implements FeedListContract.View, QiitaFeedAdapter.OnFeedItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, SwipeableRecyclerViewTouchListener.SwipeListener {

    public static final String POPULAR_FEED_TAG = "popular-items";
    private static final String TAG = QiitaFeedListActivity.class.getName();
    private QiitaFeedAdapter mAdapter;
    private List<Entry> mDataSet;
    private FeedListContract.UserActions repositoryListPresenter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.rssFeedEditText)
    EditText mEditText;
    @Bind(R.id.feedTitle)
    TextView mFeedTitle;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.fetchFeedButton)
    Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);

        final QiitaApiInterface apiInterface = ((QiitaReaderApplication) getApplication()).getQiitaApi();
        repositoryListPresenter = new QiitaFeedPresenter(this, apiInterface);

        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setListener();
        repositoryListPresenter.loadFeed(POPULAR_FEED_TAG);
    }

    private void setListener() {
        mSwipeLayout.setOnRefreshListener(this);
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView, this);
        mRecyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    @Override
    public void showFeed(Feed feed) {
        mDataSet = feed.getEntry();
        mAdapter = new QiitaFeedAdapter(feed.getEntry(), this);
        mFeedTitle.setText(feed.getTitle());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        mSwipeLayout.setRefreshing(false);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
        Snackbar.make(layout, "読み込めませんでした", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void showDetail(String fullRepositoryName) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fullRepositoryName)));
    }

    @OnClick(R.id.fetchFeedButton)
    void onClick(View view) {
        if (mEditText.getText() != null) {
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            repositoryListPresenter.loadFeed(mEditText.getText().toString());
        }
    }

    @Override
    public void onFeedItemClick(Entry item) {
        repositoryListPresenter.selectFeedItem(item);
    }

    @Override
    public void onRefresh() {
        repositoryListPresenter.loadFeed(POPULAR_FEED_TAG);
    }

    @Override
    public boolean canSwipe(int position) {
        return true;
    }

    @Override
    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
        dismissOnSwipe(reverseSortedPositions);
    }

    @Override
    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
        dismissOnSwipe(reverseSortedPositions);
        // TODO: Stock Item
    }

    private void dismissOnSwipe(int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mDataSet.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
        mAdapter.notifyDataSetChanged();

    }
}
