package com.example.peterstolcz.newsappstage1;

import android.content.Intent;
import android.widget.TextView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity
        implements LoaderCallbacks<List<News>> {


    private NewsAdapter mAdapter;

    private TextView mEmptyStateTextView;

    public static final String LOG_TAG = NewsActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "https://content.guardianapis.com/technology/artificialintelligenceai?&show-tags=contributor&api-key=276175c9-c3a5-4784-b6b5-0aa68168be40\n";

    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        ListView newsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News currentNews = mAdapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getmUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(websiteIntent);



            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newslist)
    {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_news);

        mAdapter.clear();

        if (newslist != null && !newslist.isEmpty()) {
            mAdapter.addAll(newslist);

            mEmptyStateTextView.setText(R.string.no_news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();

    }
}