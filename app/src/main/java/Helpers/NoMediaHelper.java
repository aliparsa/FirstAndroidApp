package Helpers;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ali on 9/15/2015.
 */
public class NoMediaHelper {
    public static void createNoMediaFile(String src){
          File file = new File(src+"/.nomedia");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
