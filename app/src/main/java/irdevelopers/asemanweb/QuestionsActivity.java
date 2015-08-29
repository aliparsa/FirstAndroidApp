package irdevelopers.asemanweb;

import android.annotation.TargetApi;
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
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.ListViewObjectAdapter;
import DataModel.Soal;
import Helpers.FontHelper;
import Helpers.SharedPrefHelper;
import Intefaces.CallBackAsync;
import Utilities.Webservice;


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
        forceRTLIfSupported();
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


        if (type.equals("offline") && SharedPrefHelper.contain(context, "question")) {
            // offline load

            try {
                JSONObject json = new JSONObject(SharedPrefHelper.read(context, "question"));
                ArrayList<Soal> soals = Soal.getArrayListFromJson(json.getJSONArray("content"));
                lv.setAdapter(new ListViewObjectAdapter<Soal>(context, soals));

            } catch (Exception e) {
e.printStackTrace();
            }

        } else {
            // online load

            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
            basicNameValuePairs.add(new BasicNameValuePair("tag", "question"));

            //maiinProgressBar.setVisibility(View.VISIBLE);
            showLoading();

            Webservice.postData(context, basicNameValuePairs, new CallBackAsync() {
                @Override
                public void onBeforStart() {
                    // show Loading
//                Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSuccessFinish(Object result) {
                    //maiinProgressBar.setVisibility(View.GONE);
                    hideLoading();

                    // show result
                    try {

                        if (result != null && ((String) result).length() > 1)
                            SharedPrefHelper.write(context, "question", (String) result);

                        JSONObject json = new JSONObject(result.toString());

                        ArrayList<Soal> soals = Soal.getArrayListFromJson(json.getJSONArray("content"));
                        lv.setAdapter(new ListViewObjectAdapter<Soal>(context, soals));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    //maiinProgressBar.setVisibility(View.GONE);
                    hideLoading();

                    // show error

                //    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
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
