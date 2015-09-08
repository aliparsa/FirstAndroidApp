package Helpers;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import Intefaces.CallBackFinish;

/**
 * Created by Ali on 9/5/2015.
 */
public class PageSourceDownloaderTask extends AsyncTaskLoader<String> {

    private final String url;
    private final CallBackFinish callBackFinish;

    public PageSourceDownloaderTask(Context context,String url, CallBackFinish callBackFinish) {
        super(context);
        this.url = url;
        this.callBackFinish = callBackFinish;
    }

    @Override
    public String loadInBackground() {
        String html = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
            in.close();
            html = str.toString();
        }catch (Exception e){
            e.printStackTrace();
            callBackFinish.onError(e.getMessage());
        }
        return html;
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        callBackFinish.onFinish(data);
    }
}
