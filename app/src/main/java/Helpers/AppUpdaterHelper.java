package Helpers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Intefaces.CallBackAsync;
import Intefaces.CallBackDownload;
import Intefaces.CallBackYes;
import Utilities.Webservice;
import irdevelopers.asemanweb2.R;

/**
 * Created by Alip on 8/16/2015.
 */
public class AppUpdaterHelper
{
    public static void checkForUpdate(final Context context) {
    SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(context);
    if (!myPreference.getBoolean("new_news_checkbox", true)) return;


    List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
    basicNameValuePairs.add(new BasicNameValuePair("tag", "update"));

    Webservice.postDataToAddress(context, basicNameValuePairs, ServerAddress.funcFile, new CallBackAsync<String>() {


        @Override
        public void onSuccessFinish(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int serverVersion = jsonObject.getInt("version");
                final String url = jsonObject.getString("url");

                int versionCode = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0).versionCode;

                if (serverVersion > versionCode) {
                    // show update dialog
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.item_update_dialog_title, null);

                    new AlertDialog.Builder(context)
                            .setView(view)
                            .setPositiveButton("دانلود و نصب", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(context, "در حال دانلود", Toast.LENGTH_SHORT).show();
                                    final ProgressDialog mProgressDialog = new ProgressDialog(context);
                                    mProgressDialog.setMessage("در حال دانلود");
                                    mProgressDialog.setIndeterminate(true);
                                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                    mProgressDialog.setCancelable(true);
                                    mProgressDialog.show();

                                    new DownloadTask(context, new CallBackDownload() {
                                        @Override
                                        public void onSuccessFinish(String result) {
                                            mProgressDialog.hide();

                                            final Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setDataAndType(Uri.parse("file://" + result),
                                                    "application/vnd.android.package-archive");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
//
//                                            Intent promptInstall = new Intent(Intent.ACTION_VIEW)
//                                                    .setDataAndType(Uri.parse("file://" + result),
//                                                            "application/vnd.android.package-archive");
//                                            context.startActivity(promptInstall);

                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            mProgressDialog.hide();

                                        }

                                        @Override
                                        public void onProgressUpdate(int progress) {
                                            mProgressDialog.setIndeterminate(false);
                                            mProgressDialog.setMax(100);
                                            mProgressDialog.setProgress(progress);

                                        }
                                    }).execute(url, Environment.getExternalStorageDirectory().getPath() + "/asemanweb.apk");


                                }
                            })
                            .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();


                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(String errorMessage) {

        }
    });

    }

    public static void isNewVersionExist(final Context context, final CallBackYes callBackYesNo){
        SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(context);
        if (!myPreference.getBoolean("new_news_checkbox", true)) return;


        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("tag", "update"));

        Webservice.postDataToAddress(context, basicNameValuePairs, ServerAddress.funcFile, new CallBackAsync<String>() {


            @Override
            public void onSuccessFinish(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int serverVersion = jsonObject.getInt("version");
                    final String url = jsonObject.getString("url");

                    int versionCode = context.getPackageManager()
                            .getPackageInfo(context.getPackageName(), 0).versionCode;

                    if (serverVersion > versionCode) {
                        callBackYesNo.onYesResult(serverVersion+"");
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

}


//            Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {
//                @Override
//                public void onBeforStart() {
//
//                }
//
//                @Override
//                public void onSuccessFinish(String result) {
//                    //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        int serverVersion = jsonObject.getInt("version");
//                        final String url = jsonObject.getString("url");
//
//                        int versionCode = context.getPackageManager()
//                                .getPackageInfo(context.getPackageName(), 0).versionCode;
//
//                        if (serverVersion > versionCode) {
//                            // show update dialog
//                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                            View headerView = inflater.inflate(R.layout.item_update_dialog_title, null);
//
//                            new AlertDialog.Builder(context)
//                                    .setCustomTitle(headerView)
//                                    .setMessage("نسخه جدیدی از برنامه موجود است آیا بروزرسانی مینمایید؟")
//                                    .setPositiveButton("دانلود و نصب", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            //Toast.makeText(context, "در حال دانلود", Toast.LENGTH_SHORT).show();
//                                            final ProgressDialog mProgressDialog = new ProgressDialog(context);
//                                            mProgressDialog.setMessage("در حال دانلود");
//                                            mProgressDialog.setIndeterminate(true);
//                                            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                                            mProgressDialog.setCancelable(true);
//                                            mProgressDialog.show();
//
//                                            new DownloadTask(context, new CallBackDownload() {
//                                                @Override
//                                                public void onSuccessFinish(String result) {
//                                                    mProgressDialog.hide();
//                                                    Intent promptInstall = new Intent(Intent.ACTION_VIEW)
//                                                            .setDataAndType(Uri.parse("file://" + result),
//                                                                    "application/vnd.android.package-archive");
//                                                    startActivity(promptInstall);
//
//                                                }
//
//                                                @Override
//                                                public void onError(String errorMessage) {
//                                                    mProgressDialog.hide();
//
//                                                }
//
//                                                @Override
//                                                public void onProgressUpdate(int progress) {
//                                                    mProgressDialog.setIndeterminate(false);
//                                                    mProgressDialog.setMax(100);
//                                                    mProgressDialog.setProgress(progress);
//
//                                                }
//                                            }).execute(url, Environment.getExternalStorageDirectory().getPath() + "/asemanweb.apk");
//
//
//                                        }
//                                    })
//                                    .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//                                    })
//                                    .show();
//
//
//                        }
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//
//                }
//            });

