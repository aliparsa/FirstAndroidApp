package Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.io.Serializable;

import Intefaces.CallBackAsync;

/**
 * Created by Alip on 7/26/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> implements Serializable {
    private final CallBackAsync<Bitmap> calback;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        calback.onBeforStart();
    }

    public DownloadImageTask(CallBackAsync<Bitmap> callBack) {

        this.calback = callBack;
    }


    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            calback.onError(e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        calback.onSuccessFinish(result);
    }


}
