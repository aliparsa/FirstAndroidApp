package Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by Ali on 8/29/2015.
 */
public class SliderHelper {

    public static String slide1Path = PathHelper.homePath+"/slide1.jpg";
    public static String slide2Path = PathHelper.homePath+"/slide2.jpg";
    public static String slide3Path = PathHelper.homePath+"/slide3.jpg";





    public static void loadSlide1(Context context, final ImageView imageView){


        if (Ram.slide1 != null) {
            // if exist in Ram load from ram
            imageView.setImageBitmap(Ram.slide1);

        }else{
            // if exist in Disk load from disk
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(slide1Path, options);
            Ram.slide1 = bitmap;
            imageView.setImageBitmap(bitmap);
        }

        // call online load anyway
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(ServerAddress.Slide1Url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {


            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                Ram.slide1 = bitmap;
                saveBitmap(bitmap,slide1Path);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    public static void loadSlide2(Context context, final ImageView imageView){

        if (Ram.slide2 != null) {
            // if exist in Ram load from ram
            imageView.setImageBitmap(Ram.slide2);
        }else{
            // if exist in Disk load from disk
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(slide2Path, options);
            Ram.slide2 = bitmap;
            imageView.setImageBitmap(bitmap);
        }

        // call online load anyway
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(ServerAddress.Slide2Url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {


            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                Ram.slide2 = bitmap;
                saveBitmap(bitmap,slide2Path);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    public static void loadSlide3(Context context, final ImageView imageView){

        if (Ram.slide3 != null) {
            // if exist in Ram load from ram
            imageView.setImageBitmap(Ram.slide3);
        }else{
            // if exist in Disk load from disk
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(slide3Path, options);
            Ram.slide3 = bitmap;
            imageView.setImageBitmap(bitmap);
        }

        // call online load anyway
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(ServerAddress.Slide3Url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {


            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                Ram.slide3 = bitmap;
                saveBitmap(bitmap,slide3Path);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    public static void saveBitmap(Bitmap finalBitmap,String filePath) {

        File file = new File (filePath);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
