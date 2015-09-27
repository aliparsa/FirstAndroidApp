package irdevelopers.asemanweb2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.ListViewObjectAdapter;
import DataModel.Group;
import DataModel.News;
import Helpers.ActionBarHelper;
import Helpers.DatabaseHelper;
import Helpers.NewsLoader;
import Helpers.Ram;
import Helpers.ShareHelper;
import Intefaces.CallBackFinish;
import Intefaces.OnActionBarClickListener;


public class NewsActivity extends ActionBarActivity {
    Context context;
    int page = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean loadMore = false;
    Group group;
    ListView lv;
    ArrayList<News> newses = new ArrayList<News>();
    ProgressBar footerprogressBar;
    Bundle bundle;
    private Menu menu;

    ListViewObjectAdapter<News> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



        try {
            //forceRTLIfSupported();
            context = NewsActivity.this;
            group = Ram.group;

            if (group == null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            ActionBarHelper.setBackActionbar(context, getSupportActionBar(), group.title, new OnActionBarClickListener() {
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

            getSupportActionBar().setTitle(group.title);
            lv = (ListView) findViewById(R.id.mainNewsListView);
            footerprogressBar = (ProgressBar) findViewById(R.id.progressBar2);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);


            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshContent();
                }
            });



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

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    String[] name = new String[] {"اشتراک گذاری"};
                    builder.setItems(name, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            News news = ((News.Holder) view.getTag()).news;
                            ShareHelper.share(context,news);

                        }
                    });
                    builder.show();

                    return true;
                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        this.menu = menu;
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



    public void loadNews(){
        ArrayList<News> loadedFromDb = new DatabaseHelper(context).getAllNews();
        ArrayList<News> newses = NewsLoader.filterNewsByGroup(context, loadedFromDb, group);
        if(newses.size()<1)
        {
            Toast.makeText(context,"خبری در این بخش موجود نیست",Toast.LENGTH_LONG).show();
        }
        adapter = new ListViewObjectAdapter<News>(context,newses);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(adapter!=null){
//            adapter.notifyDataSetChanged();
//        }
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
