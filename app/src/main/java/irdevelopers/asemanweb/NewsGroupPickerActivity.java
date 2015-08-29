package irdevelopers.asemanweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import Adapter.ListViewObjectAdapter;
import DataModel.Group;
import Helpers.GroupsLoader;
import Helpers.Ram;
import Helpers.SharedPrefHelper;
import Intefaces.CallBackAsync;
import Intefaces.CallBackGroup;
import Utilities.Webservice;


public class NewsGroupPickerActivity extends ActionBarActivity {
    Context context;
    ProgressBar pg;
    ListView newslv;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_group_picker);
        forceRTLIfSupported();
        context = NewsGroupPickerActivity.this;

        getSupportActionBar().setTitle("گروه بندی ها");
        getSupportActionBar().setSubtitle("یک گروه را برگزینید");
        //pg = (ProgressBar) findViewById(R.id.progressBarNews);
        newslv = (ListView) findViewById(R.id.newsListView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GroupsLoader.syncOnline(context, new CallBackGroup() {
                    @Override
                    public void onSuccess(ArrayList<Group> groups) {
                        newslv.setAdapter(new ListViewObjectAdapter<Group>(context, groups));
                        hideLoading();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        hideLoading();
                    }
                });
            }
        });



        GroupsLoader.getGroups(context, new CallBackGroup() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                newslv.setAdapter(new ListViewObjectAdapter<Group>(context, groups));
            }

            @Override
            public void onError(String errorMessage) {

            }
        });


        newslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group group = ((Group.Holder) view.getTag()).group;
                if (Ram.updatedGroups != null) Ram.updatedGroups.remove(group.code);
                Intent intent = new Intent(context, NewsActivity.class);
                if (getIntent().hasExtra("online")) {
                    intent.putExtra("online", true);
                }
                Ram.group = group;
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_news, menu);
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
}
