package irdevelopers.asemanweb2;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Intefaces.CallBackAsync;
import Utilities.Webservice;
import Views.EditTextFont;


public class TamasBaMaActivity extends ActionBarActivity {

    EditTextFont tamasbama_name;
    EditTextFont tamasbama_tel;
    EditTextFont tamasbama_email;
    EditTextFont tamasbama_message;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamas_ba_ma);
        forceRTLIfSupported();
        getSupportActionBar().setTitle("صفحه اصلی");
        context = this;
        tamasbama_name = (EditTextFont) findViewById(R.id.tamasbama_name);
        tamasbama_tel = (EditTextFont) findViewById(R.id.tamasbama_tel);
        tamasbama_email = (EditTextFont) findViewById(R.id.tamasbama_email);
        tamasbama_message = (EditTextFont) findViewById(R.id.tamasbama_message);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tamas_ba_ma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tamasbama) {
            if (tamasbama_name.length() < 3) {
                Toast.makeText(context, "نام را بررسی نمایید", Toast.LENGTH_SHORT).show();
                tamasbama_name.requestFocus();
                return false;
            }
            if (tamasbama_tel.length() < 3) {
                Toast.makeText(getApplicationContext(), "تلفن را بررسی نمایید", Toast.LENGTH_SHORT).show();
                tamasbama_tel.requestFocus();
                return false;
            }
            if (tamasbama_email.length() < 3) {
                Toast.makeText(getApplicationContext(), "ایمیل را بررسی نمایید", Toast.LENGTH_SHORT).show();
                tamasbama_email.requestFocus();
                return false;
            }
            if (tamasbama_message.length() < 3) {
                Toast.makeText(getApplicationContext(), "پیام را بررسی نمایید", Toast.LENGTH_SHORT).show();
                tamasbama_message.requestFocus();
                return false;
            }

            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
            basicNameValuePairs.add(new BasicNameValuePair("tag", "tamasBaMa"));
            basicNameValuePairs.add(new BasicNameValuePair("name", tamasbama_name.getText().toString()));
            basicNameValuePairs.add(new BasicNameValuePair("tel", tamasbama_tel.getText().toString()));
            basicNameValuePairs.add(new BasicNameValuePair("email", tamasbama_email.getText().toString()));
            basicNameValuePairs.add(new BasicNameValuePair("message", tamasbama_message.getText().toString()));

            Toast.makeText(getApplicationContext(), "در حال ارسال...", Toast.LENGTH_SHORT).show();

            Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {

                @Override
                public void onSuccessFinish(String result) {


                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("result") && jsonObject.getString("result").equals("ok")) {
                            Toast.makeText(getApplicationContext(), "پیام شما دریافت شد", Toast.LENGTH_SHORT).show();
                            tamasbama_name.setText("");
                            tamasbama_tel.setText("");
                            tamasbama_email.setText("");
                            tamasbama_message.setText("");
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
}
