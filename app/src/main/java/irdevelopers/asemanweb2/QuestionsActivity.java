package irdevelopers.asemanweb2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.ListViewObjectAdapter;
import DataModel.Soal;
import Helpers.ActionBarHelper;
import Helpers.SoalLoader;
import Intefaces.CallBackSoal;
import Intefaces.OnActionBarClickListener;


public class QuestionsActivity extends ActionBarActivity {
    ListView lv;
    //ProgressBar maiinProgressBar;
    Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        context = QuestionsActivity.this;
        //forceRTLIfSupported();

        ActionBarHelper.setBackActionbar(context, getSupportActionBar(), "صفحه اصلی", new OnActionBarClickListener() {
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
        getSupportActionBar().setTitle("صفحه اصلی");

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.activity_question_swipe_refresh_layout);
        lv = (ListView) findViewById(R.id.listView);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.item_header, null);
        ImageView headerImageView = (ImageView) headerView.findViewById(R.id.imageView);
        ProgressBar progressBar = (ProgressBar) headerView.findViewById(R.id.progressBar);
        TextView titleTextView = (TextView) headerView.findViewById(R.id.titletextView);

        headerImageView.setImageResource(R.drawable.q);
        progressBar.setVisibility(View.GONE);

        titleTextView.setText("سوالات متداول");
        //FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, titleTextView);

        //new DownloadImageTask(headerImageView,progressBar).execute(json.getString("headerImage"));
        lv.addHeaderView(headerView);

        //maiinProgressBar = (ProgressBar) findViewById(R.id.main_progressBar);

        loadQuestions("offline");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadQuestions("online");
                hideLoading();

            }
        });


    }

    private void loadQuestions(String type) {

        SoalLoader.getSoals(context, new CallBackSoal() {
            @Override
            public void onSuccess(ArrayList<Soal> soals) {
                lv.setAdapter(new ListViewObjectAdapter<Soal>(context, soals));

            }

            @Override
            public void onError(String errorMessage) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_content_viewer, menu);
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
