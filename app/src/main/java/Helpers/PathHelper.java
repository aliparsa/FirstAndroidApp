package Helpers;

import android.os.Environment;

import java.io.File;

/**
 * Created by Alip on 8/8/2015.
 */
public class PathHelper {
    public static String homePath= Environment.getExternalStorageDirectory().getPath()+"/AsemanWeb";
    public static String TejaratElectronicUrl=homePath+"/"+"tejaratelectronic.html";
    public static String AmoozeshUrl=homePath+"/"+"amoozesh.html";
    public static String TarahiWebUrl=homePath+"/"+"tarahiweb.html";
    public static String SpanserUrl=homePath+"/"+"espanser.html";
    public static String CheraAsemanWebUrl=homePath+"/"+"cheraasemanweb.html";
    public static String WebsitehaUrl=homePath+"/"+"websiteha.html";
    public static String NarmafzarhaUrl=homePath+"/"+"software.html";
    public static String TamasBaMaUrl=homePath+"/"+"tamasbama.html";
    public static String DarbareMaUrl=homePath+"/"+"darbarema.html";
    public static String Slide1Url=homePath+"/"+"slide1.jpg";
    public static String Slide2Url=homePath+"/"+"slide2.jpg";
    public static String Slide3Url=homePath+"/"+"slide3.jpg";

    public static void createHomeFolder() {
        String folder_main = "AsemanWeb";
        File f = new File(Environment.getExternalStorageDirectory(),
                folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }
    }
}
