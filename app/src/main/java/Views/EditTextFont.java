package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import Helpers.FontHelper;

/**
 * Created by Alip on 7/27/2015.
 */
public class EditTextFont extends EditText {
    public EditTextFont(Context context) {
        super(context);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context, FontHelper.Fonts.YEKAN, this);
        }
    }

    public EditTextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }
    }

    public EditTextFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            FontHelper.SetFontNormal(context,FontHelper.Fonts.YEKAN,this);
        }
    }


}
