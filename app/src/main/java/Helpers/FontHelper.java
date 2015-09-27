package Helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by ashkan on 9/1/2014.
 * Step 1
 * Create assets folder under main folder
 * Step 2
 * create fonts folder under assets folder
 * Step 3
 * copy your fonts in this folder and use them
 */
public class FontHelper {

    private static Context context;
    private static Typeface tfBKOODB;
    private static Typeface tfBKAMRAN;
    private static Typeface tfYekan;


    public static void SetFontNormal(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.NORMAL);

    }

    public static void SetFontNormal(Context context, Fonts font, Button view) {

        SetFont(context, font, view, Typeface.BOLD);

    }


    public static void SetFontBold(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.BOLD);

    }

    public static void SetFontItalic(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.ITALIC);

    }

    public static void SetFontBoldItalic(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.BOLD_ITALIC);

    }

    public static void SetFont(Context context, Fonts font, TextView view, int typeFace) {
        Typeface tf;

        switch (font) {
            case MAIN_FONT:
                if (tfBKOODB == null)
                    tfBKOODB = Typeface.createFromAsset(context.getAssets(), "fonts/BKOODB.TTF");
                view.setTypeface(tfBKOODB, typeFace);
                break;
            case BKAMRAN:
                if (tfBKAMRAN == null)
                    tfBKAMRAN = Typeface.createFromAsset(context.getAssets(), "fonts/BKAMRAN.TTF");
                view.setTypeface(tfBKAMRAN, typeFace);
                break;
            case YEKAN:
                if (tfYekan == null)
                    tfYekan = Typeface.createFromAsset(context.getAssets(), "fonts/Yekan.ttf");
                view.setTypeface(tfYekan, typeFace);
                break;

        }
    }

    public static void SetFont(Context context, Fonts font, Button view, int typeFace) {
        Typeface tf;


        switch (font) {
            case MAIN_FONT:
                if (tfBKOODB == null)
                    tfBKOODB = Typeface.createFromAsset(context.getAssets(), "fonts/BKOODB.TTF");
                view.setTypeface(tfBKOODB, typeFace);
                break;
            case BKAMRAN:
                if (tfBKAMRAN == null)
                    tfBKAMRAN = Typeface.createFromAsset(context.getAssets(), "fonts/BKAMRAN.TTF");
                view.setTypeface(tfBKAMRAN, typeFace);
                break;
            case YEKAN:
                if (tfYekan == null)
                    tfYekan = Typeface.createFromAsset(context.getAssets(), "fonts/Yekan.ttf");
                view.setTypeface(tfYekan, typeFace);
                break;

        }
    }


    public enum Fonts {

        MAIN_FONT,
        BKAMRAN,
        BKOODAK,
        YEKAN
    }

}