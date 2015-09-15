package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import Helpers.FontHelper;

public class TextViewFont extends TextView {

    public TextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }
    }

    public TextViewFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }    }

    public TextViewFont(Context context) {
        super(context);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }    }

}