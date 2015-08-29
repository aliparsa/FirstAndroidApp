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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Adapter.ListViewObjectAdapter;
import DataModel.Group;
import DataModel.News;
import Helpers.DownloadTaskHidden;
import Helpers.PathHelper;
import Helpers.Ram;
import Helpers.SharedPrefHelper;
import Intefaces.CallBackAsync;
import Utilities.Webservice;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try {
            forceRTLIfSupported();
            context = NewsActivity.this;
            group = Ram.group;

            if (group == null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            getSupportActionBar().setTitle(group.title);
            lv = (ListView) findViewById(R.id.mainNewsListView);
            footerprogressBar = (ProgressBar) findViewById(R.id.progressBar2);
            //footerprogressBar.setVisibility(View.GONE);
            //lv.addFooterView(new ProgressBar(context));
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);


            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshContent();
                }
            });


            if (getIntent().hasExtra("online"))
                    loadNews("online");
                else
                    loadNews("offline");



            setLvOnItemClickListener();
            setAutoNewsLoader();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setLvOnItemClickListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, WebActivity.class);
                News news = ((News.Holder) view.getTag()).news;
                markNewsAsReaded(news);
                Ram.news = news;
                startActivity(intent);
            }
        });
    }

    private void setAutoNewsLoader() {
        // auto loader
        final int endTrigger = 0;
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lv.getCount() != 0
                        && lv.getLastVisiblePosition() >= (lv.getCount() - 1) - endTrigger) {
                    // Do what you need to get more content.
                    //loadMore();

                    if (loadMore) {
                        //Toast.makeText(context, "دریافت ادامه خبرها...", Toast.LENGTH_SHORT).show();
                        loadMore = false;
                        loadMore();
                    }
                }
            }


        });
    }

    private void markNewsAsReaded(News news) {
        for (News tmp : newses) {
            if (tmp.uid.equals(news.uid)) {
                tmp.readed = true;
            }
        }
    }

    private void refreshContent() {
        page = 1;
        loadNews("online");
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

    public void loadNews(String type) {
        try {


            if (type.equals("offline") && SharedPrefHelper.read(context, group.code.toString()) != null) {

                //menu.getItem(0).setIcon(R.drawable.ic_cloud_off_white_24dp);
                ArrayList<News> cashedNews = News.getArrayListFromJson(new JSONArray(SharedPrefHelper.read(context, group.code.toString())));
                lv.setAdapter(new ListViewObjectAdapter<News>(context, cashedNews));


            } else {
                //menu.getItem(0).setIcon(R.drawable.ic_cloud_queue_white_24dp);
                showLoading();
                List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
                basicNameValuePairs.add(new BasicNameValuePair("tag", "news"));
                basicNameValuePairs.add(new BasicNameValuePair("groupCode", group.code.toString()));
                basicNameValuePairs.add(new BasicNameValuePair("page", page + ""));
                basicNameValuePairs.add(new BasicNameValuePair("count", 50 + ""));
                Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {
                    @Override
                    public void onBeforStart() {

                    }

                    @Override
                    public void onSuccessFinish(String result) {
                        hideLoading();



                        if ( result!=null && result.length()<2 ) {
                            Toast.makeText(context,"خبری موجود نیست",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            JSONObject json = new JSONObject(result);
                            if (json.getString("haveContinue").equals("yes")) {
                                loadMore = true;
                                page++;
                            }
                            newses.clear();
                            newses.addAll(News.getArrayListFromJson(json.getJSONArray("content")));

                            // check for last news id
                            for(News news : newses){
                                int newsUid = Integer.parseInt(news.uid);
                                int lastUid = Integer.parseInt(SharedPrefHelper.read(context,"lastNewsUid"));
                                if (newsUid>lastUid)
                                    SharedPrefHelper.write(context,"lastNewsUid",newsUid+"");
                            }

                            //SharedPrefHelper.write(context, "lastNewsUid", newses.get(0).uid);
                            downloadNewsPages(newses);
                            storeNewsesArrayList();
                            lv.setAdapter(new ListViewObjectAdapter<News>(context, newses));


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "server responce not valid !", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(String errorMessage) {
                        hideLoading();

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void storeNewsesArrayList() {
        String str = News.getJsonFromArrayList(newses);
        SharedPrefHelper.write(context, group.code.toString(), str);
    }

    private void downloadNewsPages(ArrayList<News> newses) {
        for (News news : newses) {
            File file = new File(PathHelper.homePath + "/" + news.uid + ".html");
            if (!file.exists())
                new DownloadTaskHidden(context).execute(news.url, PathHelper.homePath + "/" + news.uid + ".html");
        }
    }

    public void loadMore() {

        footerprogressBar.setVisibility(View.VISIBLE);
        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("tag", "news"));
        basicNameValuePairs.add(new BasicNameValuePair("groupCode", group.code.toString()));
        basicNameValuePairs.add(new BasicNameValuePair("page", page + ""));
        basicNameValuePairs.add(new BasicNameValuePair("count", 20 + ""));

        Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {
            @Override
            public void onBeforStart() {

            }

            @Override
            public void onSuccessFinish(String result) {
                footerprogressBar.setVisibility(View.GONE);

                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("haveContinue").equals("yes")) {
                        loadMore = true;
                        page++;
                    }
                    newses.addAll(News.getArrayListFromJson(json.getJSONArray("content")));
                    downloadNewsPages(newses);

                    storeNewsesArrayList();

                    ((ListViewObjectAdapter<News>) lv.getAdapter()).items = newses;
                    ((ListViewObjectAdapter<News>) lv.getAdapter()).notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "server responce not valid !", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onError(String errorMessage) {
                footerprogressBar.setVisibility(View.GONE);

            }
        });
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
