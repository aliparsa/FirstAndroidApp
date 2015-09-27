package irdevelopers.asemanweb2;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import DataModel.News;
import Helpers.ActionBarHelper;
import Helpers.AppUpdaterHelper;
import Helpers.DatabaseHelper;
import Helpers.DownloadTask;
import Helpers.DownloadTaskHidden;
import Helpers.GroupsLoader;
import Helpers.NewsLoader;
import Helpers.NoMediaHelper;
import Helpers.PackageInstalledCheker;
import Helpers.PathHelper;
import Helpers.ServerAddress;
import Helpers.SharedPrefHelper;
import Helpers.SliderHelper;
import Helpers.SoalLoader;
import Helpers.StatisticsHelper;
import Intefaces.CallBackDownload;
import Intefaces.OnActionBarClickListener;
import Views.TextViewFont;


public class MainActivity extends ActionBarActivity {
    public TableRow tableRow;
    Context context = MainActivity.this;
    ImageView headerImageView;
    ImageView slide1;
    ImageView slide2;
    ImageView slide3;
    LinearLayout soalatMotedavel;
    LinearLayout tazeha;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout tejaratElectronic;
    private LinearLayout amoozesh;
    private LinearLayout websiteha;
    private LinearLayout cheraAsemanWeb;
    private LinearLayout espanser;
    private LinearLayout tarahiweb;
    private LinearLayout darbareMa;
    private LinearLayout tamasBaMa;
    private LinearLayout narmafzarha;
    private LinearLayout sabtNam;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set activity title
        getSupportActionBar().setTitle("آسمان وب");
        // view and variable init
        final Context context = MainActivity.this;

        // close the app is deadline reached
        //DeveloperHelper.checkDeadline(this);

//        try{
//            int versionCode = context.getPackageManager()
//                    .getPackageInfo(context.getPackageName(), 0).versionCode;
//            Toast.makeText(context,versionCode+"",Toast.LENGTH_SHORT).show();
//        }catch (Exception e){e.printStackTrace();}
//        //clear app folder


        GroupsLoader.syncSilent(context);
        SoalLoader.syncSilent(context);
        NewsLoader.syncSilent(context);


        // clear app prefrence
        try {
            if (!SharedPrefHelper.contain(context, "isFirstRun1")) {
                SharedPrefHelper.clear(context);
                DeleteRecursive(new File(PathHelper.homePath));
                SharedPrefHelper.write(context, "isFirstRun1", "yes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!SharedPrefHelper.contain(getApplicationContext(), "lastNewsUid"))
            SharedPrefHelper.write(getApplicationContext(), "lastNewsUid", "0");

        setContentView(R.layout.activity_main);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        //start service


        //forceRTLIfSupported();
        getSupportActionBar().setSubtitle("برترین ارائه دهنده خدمات تجارت الکترونیک");

        ActionBarHelper.setIconSettingActionbar(context, getSupportActionBar(), "آسمان وب", new OnActionBarClickListener() {
            @Override
            public void onBackPressed() {

            }

            @Override
            public void onReloadPressed() {

            }

            @Override
            public void onSendPresses() {

            }

            @Override
            public void onSettingPresses() {
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);            }
        });


        slide1 = (ImageView) findViewById(R.id.slide1);
        slide2 = (ImageView) findViewById(R.id.slide2);
        slide3 = (ImageView) findViewById(R.id.slide3);

        tableRow = (TableRow) findViewById(R.id.firstTableRow);

        // start watchdogs service
        Intent intent = new Intent(this, WatchDogService.class);
        startService(intent);

        Intent intent2 = new Intent(this, WatchDogNotification.class);
        startService(intent2);


        // create Home folder
        PathHelper.createHomeFolder();

        // add nomedia to app folder
        NoMediaHelper.createNoMediaFile(PathHelper.homePath);

        // send statistics to server
        StatisticsHelper.sendStatisticsToServer(context);

        // try to unistall old version
        if(PackageInstalledCheker.isPackageInstalled("irdevelopers.asemanweb",context)){
            // yes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View header = inflater.inflate(R.layout.dialog_unistal_last_ver, null);
            new AlertDialog.Builder(context)
                    .setView(header)
                    .setPositiveButton("حذف نسخه قبلی", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri packageUri = Uri.parse("package:irdevelopers.asemanweb");
                            Intent uninstallIntent =
                                    new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                            startActivity(uninstallIntent);

                        }
                    })

                    .show();
        }

        downloadMainPages();

        copyAssets();

        ArrayList<News> newses = new DatabaseHelper(context).getAllNews();


        // flipviewer
        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        headerImageView = (ImageView) findViewById(R.id.imageView);

        soalatMotedavel = (LinearLayout) findViewById(R.id.menu_soalat_motedavel);
        sabtNam = (LinearLayout) findViewById(R.id.menu_sabt_nam);
        tazeha = (LinearLayout) findViewById(R.id.menu_tazeha);
        tejaratElectronic = (LinearLayout) findViewById(R.id.menu_tejarat_electronic);
        amoozesh = (LinearLayout) findViewById(R.id.menu_amoozesh);
        websiteha = (LinearLayout) findViewById(R.id.menu_websiteha);
        cheraAsemanWeb = (LinearLayout) findViewById(R.id.menu_chera_asemanweb);
        espanser = (LinearLayout) findViewById(R.id.menu_espanser);
        darbareMa = (LinearLayout) findViewById(R.id.menu_darbareye_ma);
        tamasBaMa = (LinearLayout) findViewById(R.id.menu_tamas_bama);
        narmafzarha = (LinearLayout) findViewById(R.id.menu_narmafzarha);
        tarahiweb = (LinearLayout) findViewById(R.id.menu_tarahi_web);


        tarahiweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainPagesWebActivity.class);
                intent.putExtra("offlinePath", PathHelper.TarahiWebUrl);
                intent.putExtra("onlinePath", ServerAddress.TarahiWebUrl);
                startActivity(intent);
            }
        });

        narmafzarha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("offlinePath", PathHelper.NarmafzarhaUrl);
                intent.putExtra("onlinePath", ServerAddress.NarmafzarhaUrl);
                startActivity(intent);
            }
        });

        tamasBaMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TamasBaMaActivity.class);
                startActivity(intent);
            }
        });

        darbareMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("offlinePath", PathHelper.DarbareMaUrl);
                intent.putExtra("onlinePath", ServerAddress.DarbareMaUrl);
                startActivity(intent);
            }
        });

        espanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("offlinePath", PathHelper.SpanserUrl);
                intent.putExtra("onlinePath", ServerAddress.SpanserUrl);
                startActivity(intent);
            }
        });

        cheraAsemanWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("offlinePath", PathHelper.CheraAsemanWebUrl);
                intent.putExtra("onlinePath", ServerAddress.CheraAsemanWebUrl);

                startActivity(intent);
            }
        });

        websiteha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("offlinePath", PathHelper.WebsitehaUrl);
                intent.putExtra("onlinePath", ServerAddress.WebsitehaUrl);
                startActivity(intent);
            }
        });

        amoozesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("offlinePath", PathHelper.AmoozeshUrl);
                intent.putExtra("onlinePath", ServerAddress.AmoozeshUrl);
                startActivity(intent);
            }
        });
        tejaratElectronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("offlinePath", PathHelper.TejaratElectronicUrl);
                intent.putExtra("onlinePath", ServerAddress.TejaratElectronicUrl);
                startActivity(intent);
            }
        });

        tazeha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsGroupPickerActivity.class);
                startActivity(intent);
            }
        });

        soalatMotedavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionsActivity.class);
                intent.putExtra("tag", "question");
                startActivity(intent);
            }
        });

        sabtNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RigesterActivity.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadMainPage();
            }
        });


        AppUpdaterHelper.checkForUpdate(context);

        progressBar = (ProgressBar) findViewById(R.id.slider_progress);
        SliderHelper.loadSlide1(context, slide1,progressBar);
        SliderHelper.loadSlide2(context, slide2,progressBar);
        SliderHelper.loadSlide3(context, slide3,progressBar);


    }


    private void whatIsNew() {
        try {


            if (SharedPrefHelper.contain(context, "versionCode")) {
                int sharedVersionCode = Integer.parseInt(SharedPrefHelper.read(context, "versionCode"));
                int appVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
                if (appVersionCode > sharedVersionCode) {
                    showWhatsNewDialog();
                    SharedPrefHelper.write(context, "versionCode", appVersionCode + "");
                }
            } else {
                int appVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
                showWhatsNewDialog();
                SharedPrefHelper.write(context, "versionCode", appVersionCode + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showWhatsNewDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.item_whats_new_dialog_title, null);

        new AlertDialog.Builder(context)
                .setCustomTitle(headerView)
                .setMessage("تغییر آیکن برنامه" + "\n" + "اضافه شدن پیام تازه ها در این نسخه")
                .show();

    }

    private void downloadMainPages() {
        // download tejarat electronix
        new DownloadTaskHidden(context).execute(ServerAddress.TejaratElectronicUrl, PathHelper.TejaratElectronicUrl);
        new DownloadTaskHidden(context).execute(ServerAddress.AmoozeshUrl, PathHelper.AmoozeshUrl);
        new DownloadTaskHidden(context).execute(ServerAddress.TarahiWebUrl, PathHelper.TarahiWebUrl);
        new DownloadTaskHidden(context).execute(ServerAddress.SpanserUrl, PathHelper.SpanserUrl);
        new DownloadTaskHidden(context).execute(ServerAddress.CheraAsemanWebUrl, PathHelper.CheraAsemanWebUrl);
        new DownloadTaskHidden(context).execute(ServerAddress.WebsitehaUrl, PathHelper.WebsitehaUrl);
        new DownloadTaskHidden(context).execute(ServerAddress.NarmafzarhaUrl, PathHelper.NarmafzarhaUrl);
        new DownloadTaskHidden(context).execute(ServerAddress.DarbareMaUrl, PathHelper.DarbareMaUrl);

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            Intent intent = new Intent(context, SettingsActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void showLoading() {
        try {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoading() {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();
    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("bootstrap.min.css");
            File outFile = new File(PathHelper.homePath + "/" + "bootstrap.min.css");
            out = new FileOutputStream(outFile);
            copyFile(in, out);
        } catch (IOException e) {
            Log.e("tag", "Failed to copy asset file: " + "bootstrap.min.css", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void reloadMainPage() {
        SliderHelper.loadSlide1(context, slide1,progressBar);
        SliderHelper.loadSlide2(context, slide2,progressBar);
        SliderHelper.loadSlide3(context, slide3,progressBar);
        downloadMainPages();
        hideLoading();
    }
}
