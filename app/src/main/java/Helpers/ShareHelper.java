package Helpers;

import android.content.Context;
import android.content.Intent;

import DataModel.News;

/**
 * Created by Ali on 9/13/2015.
 */
public class ShareHelper {

    public static void share(Context context, News news){
        String shareBody = news.title ;
        shareBody+="\n\n";
        shareBody+=news.url;
        shareBody+="\n\n";
        shareBody+="خبر خوان آسمان وب را دانلود نمایید";
        shareBody+="\n\n";
        shareBody+="http://www.myhost.ir/asemanweb.apk";


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}
