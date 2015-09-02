package Helpers;

import android.content.Context;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import DataModel.Group;
import DataModel.News;
import Intefaces.CallBackAsync;
import Intefaces.CallBackFinish;
import Intefaces.CallBackNews;
import Utilities.Webservice;

/**
 * Created by Ali on 8/30/2015.
 */
public class NewsLoader {

    public static final String MAX_UID = "MAX_UID";

    public static ArrayList<News> newses = new ArrayList<News>();

    public static ArrayList<News> getNews(Context context, Group group) {
        try {
            if (SharedPrefHelper.contain(context, group.code.toString())) {
                ArrayList<News> news = News.getArrayListFromJson(new JSONArray(SharedPrefHelper.read(context, group.code.toString())));
                if (news.size() > 0)
                    return news;
                else
                    return null;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public static Integer getMaxUid(Context context) {
        if (SharedPrefHelper.contain(context,MAX_UID))
                return Integer.parseInt(SharedPrefHelper.read(context,MAX_UID));
        else
            return 0;
    }

    public static void setMaxUid(Context context, String uid) {

        SharedPrefHelper.write(context, MAX_UID, uid + "");
    }


    public static void syncSilent(final Context context) {
        try {
            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
            basicNameValuePairs.add(new BasicNameValuePair("tag", "lastUpdate"));
            basicNameValuePairs.add(new BasicNameValuePair("uid", getMaxUid(context).toString()));
            Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {

                @Override
                public void onSuccessFinish(String result) {

                    if (!result.equals("0")) {
                        ArrayList<News> newses = new ArrayList<News>();
                        try {

                            // create Data Model
                            JSONObject json = new JSONObject(result);
                            newses.addAll(News.getArrayListFromJson(json.getJSONArray("content")));

                            // add data model To DB
                            for(News news:newses)
                                new DatabaseHelper(context).insertNews(news);

                            // save max uid
                            setMaxUid(context, new DatabaseHelper(context).getLastNews().uid);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onError(String errorMessage) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void syncOnline(final Context context, final CallBackFinish callBackFinish) {
        try {
            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
            basicNameValuePairs.add(new BasicNameValuePair("tag", "lastUpdate"));
            basicNameValuePairs.add(new BasicNameValuePair("uid", getMaxUid(context).toString()));
            Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {

                @Override
                public void onSuccessFinish(String result) {

                    if (!result.equals("0")) {
                        ArrayList<News> newses = new ArrayList<News>();
                        try {

                            // create Data Model
                            JSONObject json = new JSONObject(result);
                            newses.addAll(News.getArrayListFromJson(json.getJSONArray("content")));

                            // add data model To DB
                            for(News news:newses)
                                new DatabaseHelper(context).insertNews(news);

                            // save max uid
                            setMaxUid(context, new DatabaseHelper(context).getLastNews().uid);

                            callBackFinish.onFinish("");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBackFinish.onError(e.getMessage());
                        }

                    }else {
                        callBackFinish.onFinish("");
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    callBackFinish.onError(errorMessage);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void addToLocalNews(Context context, Group group, ArrayList<News> newNews) {
        ArrayList<News> oldNews = getNews(context, group);
        for (News news : newNews) {
            if (!oldNews.contains(news)) {
                oldNews.add(news);
            }
        }

        String str = News.getJsonFromArrayList(oldNews);
        SharedPrefHelper.write(context, group.code.toString(), str);
    }

    private static void downloadNewsPages(Context context, ArrayList<News> newses) {
        for (News news : newses) {
            File file = new File(PathHelper.homePath + "/" + news.uid + ".html");
            if (!file.exists())
                new DownloadTaskHidden(context).execute(news.url, PathHelper.homePath + "/" + news.uid + ".html");
        }
    }

    public static  ArrayList<News> filterNewsByGroup(Context context, ArrayList<News> unfilteredNewses,Group group){
        ArrayList<DataModel.News> newses = new ArrayList<DataModel.News>();
        for(DataModel.News news : unfilteredNewses){
            if(news.groupCode.endsWith(group.code)){
                newses.add(news);
            }
        }
        return newses;
    }
}
