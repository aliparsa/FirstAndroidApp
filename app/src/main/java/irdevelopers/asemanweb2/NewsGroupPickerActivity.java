package irdevelopers.asemanweb2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import Adapter.ListViewObjectAdapter;
import DataModel.Group;
import DataModel.News;
import Helpers.ActionBarHelper;
import Helpers.DatabaseHelper;
import Helpers.GroupsLoader;
import Helpers.Ram;
import Intefaces.CallBackGroup;
import Intefaces.OnActionBarClickListener;
import Views.TextViewFont;


public class NewsGroupPickerActivity extends ActionBarActivity {
    Context context;
    ProgressBar pg;
    ListView newslv;

    ListViewObjectAdapter<Group> adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_group_picker);
        //forceRTLIfSupported();
        context = NewsGroupPickerActivity.this;


        ActionBarHelper.setBackActionbar(context, getSupportActionBar(), "گروه بندی ها", new OnActionBarClickListener() {
            @Override
            public void onBackPressed() {
                ((Activity) context).finish();
            }

            @Override
            public void onReloadPressed() {

            }

            @Override
            public void onSendPresses() {

            }

            @Override
            public void onSettingPresses() {

            }

        });

        //getSupportActionBar().setTitle("گروه بندی ها");
       // getSupportActionBar().setSubtitle("یک گروه را برگزینید");
        //pg = (ProgressBar) findViewById(R.id.progressBarNews);
        newslv = (ListView) findViewById(R.id.newsListView);


        // set view all news item to listview header
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        header = (RelativeLayout) inflater.inflate(R.layout.item_group, null);
        TextViewFont textViewFont = (TextViewFont) header.findViewById(R.id.title);
        textViewFont.setText("همه اخبار");
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllNewsActivity.class);
                startActivity(intent);
            }
        });
        newslv.addHeaderView(header);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GroupsLoader.syncOnline(context, new CallBackGroup() {
                    @Override
                    public void onSuccess(ArrayList<Group> groups) {

                        ArrayList<News> newses = new DatabaseHelper(context).getAllUnReadNews();
                        for (Group group : groups) {
                            for (News news : newses) {
                                String[] groupCodes = news.groupCode.split(",");
                                for (String code : groupCodes) {
                                    if (group.code.equals(code)) {
                                        group.unreadInside = true;
                                    }
                                }
                            }
                        }
                        adapter = new ListViewObjectAdapter<Group>(context, groups);
                        newslv.setAdapter(adapter);

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

                ArrayList<News> newses = new DatabaseHelper(context).getAllUnReadNews();
                for (Group group : groups) {
                    for (News news : newses) {
                        String[] groupCodes = news.groupCode.split(",");

                        for (String code : groupCodes) {
                            if (group.code.equals(code)) {
                                group.unreadInside = true;
                            }
                        }
                    }
                }

                adapter = new ListViewObjectAdapter<Group>(context, groups);
                newslv.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });





        newslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Group group = ((Group.Holder) view.getTag()).group;
                //if (Ram.updatedGroups != null) Ram.updatedGroups.remove(group.code);
                Intent intent = new Intent(context, NewsActivity.class);
                if (getIntent().hasExtra("online")) {
                    intent.putExtra("online", true);
                }
                Ram.group = group;
                startActivity(intent);
            }
        });

     //   throw new RuntimeException("error");

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
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
