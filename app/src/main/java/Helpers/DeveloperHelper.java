package Helpers;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import irdevelopers.asemanweb.CheckForUpdateService;

/**
 * Created by Alip on 8/8/2015.
 */
public class DeveloperHelper {
    private static java.lang.String DEADLINE= "18/8/2015";


    public static void checkDeadline(Activity activity) {
        try {


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date strDate = sdf.parse(DEADLINE);
            if (new Date().after(strDate)) {
                activity.finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
