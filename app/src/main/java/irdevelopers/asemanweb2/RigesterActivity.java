package irdevelopers.asemanweb2;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Intefaces.CallBackAsync;
import Utilities.Webservice;


public class RigesterActivity extends ActionBarActivity {
    Context context;
    int ZamineHamkari = -1;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);
        context = RigesterActivity.this;
        forceRTLIfSupported();
        getSupportActionBar().setTitle("صفحه اصلی");

        // create and fill header
        ImageView headerImageView = (ImageView) findViewById(R.id.imageView);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView titleTextView = (TextView) findViewById(R.id.titletextView);

        titleTextView.setText("ثبت نام");

        final Button choiseWorkButton = (Button) findViewById(R.id.buttonChoiseWork);
        choiseWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence works[] = new CharSequence[]{"فروشگاه اینترنتی", "نماینده", "همکار", "نشست هم اندیشی تجارت الکترونیک"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("زمینه همکاری");
                // create and fill header
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View headerView = inflater.inflate(R.layout.item_work_picker_title, null);
                builder.setCustomTitle(headerView);


                builder.setItems(works, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choiseWorkButton.setText(works[which]);
                        ZamineHamkari = which;

                    }
                });
                builder.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rigester, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rigester) {
            final EditText name = (EditText) findViewById(R.id.rig_name);
            final EditText tel = (EditText) findViewById(R.id.rig_tel);
            final EditText email = (EditText) findViewById(R.id.rig_email);
            final Button zamine = (Button) findViewById(R.id.buttonChoiseWork);


            if (name.length() < 3) {
                Toast.makeText(getApplicationContext(), "نام و نام خانوادگی را بررسی نمایید", Toast.LENGTH_SHORT).show();
                name.requestFocus();
                return false;
            }
            if (tel.length() < 3) {
                Toast.makeText(getApplicationContext(), "تلفن را بررسی نمایید", Toast.LENGTH_SHORT).show();
                tel.requestFocus();
                return false;
            }
            if (email.length() < 3) {
                Toast.makeText(getApplicationContext(), "ایمیل را بررسی نمایید", Toast.LENGTH_SHORT).show();
                email.requestFocus();
                return false;
            }
            if (ZamineHamkari == -1) {
                Toast.makeText(getApplicationContext(), "زمینه همکاری را بررسی نمایید", Toast.LENGTH_SHORT).show();
                zamine.requestFocus();
                return false;
            }


            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
            basicNameValuePairs.add(new BasicNameValuePair("tag", "register"));
            basicNameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));
            basicNameValuePairs.add(new BasicNameValuePair("tel", tel.getText().toString()));
            basicNameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
            basicNameValuePairs.add(new BasicNameValuePair("typeCode", ZamineHamkari + ""));

            Toast.makeText(getApplicationContext(), "در حال ارسال...", Toast.LENGTH_SHORT).show();

            Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {


                @Override
                public void onSuccessFinish(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("result") && jsonObject.getString("result").equals("ok")) {
                            Toast.makeText(getApplicationContext(), "اطلاعات شما دریافت شد", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            tel.setText("");
                            email.setText("");
                            ZamineHamkari = -1;
                            zamine.setText("انتخاب زمینه همکاری");
                        } else {
                            Toast.makeText(getApplicationContext(), "خطایی در برقراری ارتباط رخ داد", Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(getApplicationContext(), "خطایی در برقراری ارتباط رخ داد", Toast.LENGTH_SHORT).show();

                }
            });

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
