package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Ali on 8/30/2015.
 */
public class ImageViewFit extends ImageView {
    public ImageViewFit(Context context) {
        super(context);
    }

    public ImageViewFit(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewFit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(!isInEditMode()){
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        setMeasuredDimension(width, height);
        }
}}
