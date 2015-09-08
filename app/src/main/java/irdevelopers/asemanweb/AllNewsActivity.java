package irdevelopers.asemanweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.ListViewObjectAdapter;
import DataModel.News;
import Helpers.DatabaseHelper;
import Helpers.NewsLoader;
import Helpers.Ram;
import Intefaces.CallBackFinish;

public class AllNewsActivity extends ActionBarActivity {

    private ListView lv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private ListViewObjectAdapter<News> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        context = AllNewsActivity.this;
        forceRTLIfSupported();
        lv = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_all_news_swipe_refresh_layout);

        loadNews();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, WebActivity.class);
                News news = ((News.Holder) view.getTag()).news;
                ((News.Holder) view.getTag()).title.setTextColor(Color.parseColor("#8c8c8c"));
                new DatabaseHelper(context).makeNewsReaded(news);

                Ram.news = news;
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void showLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void hideLoading() {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void loadNews(){
        ArrayList<News> loadedFromDb = new DatabaseHelper(context).getAllNews();
        adapter = new ListViewObjectAdapter<News>(context,loadedFromDb);
        lv.setAdapter(adapter);
    }

    private void refreshContent() {
        NewsLoader.syncOnline(context, new CallBackFinish() {
            @Override
            public void onFinish(String result) {
                loadNews();
                hideLoading();
            }

            @Override
            public void onError(String errorMessage) {
                hideLoading();

            }
        });
    }
}
