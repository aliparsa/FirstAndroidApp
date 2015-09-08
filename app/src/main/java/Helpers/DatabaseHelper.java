package Helpers;

/**
 * Created by Ali on 9/2/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;


import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DataModel.Group;
import DataModel.News;

/**
 * Created by aliparsa on 9/20/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db.db";

    // Contacts table names
    private static final String TABLE_NEWS = "news";



    // Contacts Key names

    //TABLE__NEWS
    private static final String NEWS_GROUP_CODE = "groupCode";
    private static final String NEWS_KEY_TITLE = "title";
    private static final String NEWS_KEY_IMAGE = "image";
    private static final String NEWS_KEY_CONTENT = "content";
    private static final String NEWS_KEY_URL = "url";
    private static final String NEWS_KEY_UID = "uid";
    private static final String NEWS_KEY_READED = "readed";
    private static final String NEWS_KEY_NOTIFIED = "notified";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_PERSONNEL_TABLE =
                "CREATE TABLE " + TABLE_NEWS + "("
                        + NEWS_KEY_UID + " INTEGER PRIMARY KEY,"
                        + NEWS_KEY_TITLE + " TEXT,"
                        + NEWS_KEY_CONTENT + " TEXT,"
                        + NEWS_KEY_URL + " TEXT,"
                        + NEWS_KEY_IMAGE + " TEXT,"
                        + NEWS_GROUP_CODE + " TEXT,"
                        + NEWS_KEY_READED + " TEXT,"
                        + NEWS_KEY_NOTIFIED + " TEXT,"
                        + "UNIQUE("+NEWS_KEY_UID+")"
                        + ")";
        db.execSQL(CREATE_PERSONNEL_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void insertNews(News news) {
        try {
            ContentValues values = new ContentValues();
            values.put(NEWS_KEY_UID, news.uid);
            values.put(NEWS_KEY_TITLE, news.title);
            values.put(NEWS_KEY_CONTENT, news.content);
            values.put(NEWS_KEY_URL, news.url);
            values.put(NEWS_KEY_IMAGE, news.image);
            values.put(NEWS_GROUP_CODE, news.groupCode);
            values.put(NEWS_KEY_READED, news.readed);
            values.put(NEWS_KEY_NOTIFIED, news.nofified);
            this.getWritableDatabase().insert(TABLE_NEWS, null, values);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public ArrayList<News> getAllNews() {
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS + " ORDER BY " + NEWS_KEY_UID + " DESC", null);
        ArrayList<News> newses = new ArrayList<News>();


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    News  news = new News();
                    news.uid=""+(cursor.getInt(cursor.getColumnIndex(NEWS_KEY_UID)));
                    news.title=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_TITLE)));
                    news.content=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_CONTENT)));
                    news.url=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_URL)));
                    news.image=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_IMAGE))));
                    news.groupCode=(cursor.getString(cursor.getColumnIndex(NEWS_GROUP_CODE)));
                    news.readed=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_READED))));
                    news.nofified=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_NOTIFIED))));


                    newses.add(news);

                } while (cursor.moveToNext());

            }
        }
        return newses;
    }

    public ArrayList<News> getAllNewsOfGroup(Group group) {
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS + " WHERE " + NEWS_GROUP_CODE+" ='"+group.code+"' ", null);
        ArrayList<News> newses = new ArrayList<News>();


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    News  news = new News();
                    news.uid=""+(cursor.getInt(cursor.getColumnIndex(NEWS_KEY_UID)));
                    news.title=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_TITLE)));
                    news.content=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_CONTENT)));
                    news.url=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_URL)));
                    news.image=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_IMAGE))));
                    news.groupCode=(cursor.getString(cursor.getColumnIndex(NEWS_GROUP_CODE)));
                    news.readed=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_READED))));
                    news.nofified=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_NOTIFIED))));


                    newses.add(news);

                } while (cursor.moveToNext());

            }
        }
        return newses;
    }

//    public ArrayList<News> getAllUnreadNewsOfGroup(Group group) {
//        SQLiteDatabase db = getReadableDatabase();
//        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS + " WHERE " + NEWS_GROUP_CODE+" ='"+group.code+"' AND "+NEWS_KEY_READED + " ='no' ", null);
//        ArrayList<News> newses = new ArrayList<News>();
//
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//
//                do {
//                    News  news = new News();
//                    news.uid=""+(cursor.getInt(cursor.getColumnIndex(NEWS_KEY_UID)));
//                    news.title=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_TITLE)));
//                    news.content=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_CONTENT)));
//                    news.url=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_URL)));
//                    news.image=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_IMAGE))));
//                    news.groupCode=(cursor.getString(cursor.getColumnIndex(NEWS_GROUP_CODE)));
//                    news.readed=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_READED))));
//                    news.nofified=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_NOTIFIED))));
//
//
//                    newses.add(news);
//
//                } while (cursor.moveToNext());
//
//            }
//        }
//        return newses;
//    }

    public ArrayList<News> getAllUnNotifiedNews() {
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS + " WHERE "+NEWS_KEY_NOTIFIED+" <>'yes'", null);
        ArrayList<News> newses = new ArrayList<News>();


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    News  news = new News();
                    news.uid=""+(cursor.getInt(cursor.getColumnIndex(NEWS_KEY_UID)));
                    news.title=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_TITLE)));
                    news.content=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_CONTENT)));
                    news.url=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_URL)));
                    news.image=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_IMAGE))));
                    news.groupCode=(cursor.getString(cursor.getColumnIndex(NEWS_GROUP_CODE)));
                    news.readed=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_READED))));
                    news.nofified=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_NOTIFIED))));


                    newses.add(news);

                } while (cursor.moveToNext());

            }
        }
        return newses;
    }

    public News getLastNews() {
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS +" ORDER BY "+NEWS_KEY_UID+" DESC", null);
        News  news = new News();

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                    news.uid=""+(cursor.getInt(cursor.getColumnIndex(NEWS_KEY_UID)));
                    news.title=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_TITLE)));
                    news.content=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_CONTENT)));
                    news.url=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_URL)));
                    news.image=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_IMAGE))));
                    news.groupCode=(cursor.getString(cursor.getColumnIndex(NEWS_GROUP_CODE)));
                    news.readed=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_READED))));
                    news.nofified=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_NOTIFIED))));
            }
        }
        return news;
    }



    public void emptyNewsTable() {
        getReadableDatabase().execSQL("Delete from " + TABLE_NEWS);
    }

    public void makeNewsNotified(ArrayList<News> newses){

        if (newses == null || newses.size()==0) return;
        String uides = "(";
        for(int i=0;i<newses.size();i++){
            if(i!=0) uides+=",";
            uides+=newses.get(i).uid;
        }
        uides+=")";
        getReadableDatabase().execSQL(" UPDATE "+TABLE_NEWS+ " SET " + NEWS_KEY_NOTIFIED +"='yes'  WHERE "+NEWS_KEY_UID+" IN "+uides);
    }

    public void makeNewsReaded(News news){
        getReadableDatabase().execSQL(" UPDATE "+TABLE_NEWS+ " SET " + NEWS_KEY_READED +"='yes'  WHERE "+NEWS_KEY_UID+"="+news.uid);
    }


    public ArrayList<News> getAllUnReadNews() {
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS + " WHERE "+NEWS_KEY_READED+" ='no'", null);
        ArrayList<News> newses = new ArrayList<News>();


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    News  news = new News();
                    news.uid=""+(cursor.getInt(cursor.getColumnIndex(NEWS_KEY_UID)));
                    news.title=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_TITLE)));
                    news.content=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_CONTENT)));
                    news.url=(cursor.getString(cursor.getColumnIndex(NEWS_KEY_URL)));
                    news.image=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_IMAGE))));
                    news.groupCode=(cursor.getString(cursor.getColumnIndex(NEWS_GROUP_CODE)));
                    news.readed=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_READED))));
                    news.nofified=((cursor.getString(cursor.getColumnIndex(NEWS_KEY_NOTIFIED))));


                    newses.add(news);

                } while (cursor.moveToNext());

            }
        }
        return newses;
    }

    public void saveContentOfNews(News news,String content) {
        getReadableDatabase().execSQL(" UPDATE "+TABLE_NEWS+ " SET " + NEWS_KEY_CONTENT +"='"+content+"'  WHERE "+NEWS_KEY_UID+"="+news.uid);

    }
}