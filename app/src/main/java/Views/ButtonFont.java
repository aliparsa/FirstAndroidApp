package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import Helpers.FontHelper;

public class ButtonFont extends Button {


    public ButtonFont(Context context) {
        super(context);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }
    }

    public ButtonFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }
    }

    public ButtonFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }
    }


}