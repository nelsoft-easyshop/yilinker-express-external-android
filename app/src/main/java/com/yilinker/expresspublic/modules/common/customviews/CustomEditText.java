package com.yilinker.expresspublic.modules.common.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.yilinker.expresspublic.R;

/**
 * Created by Jeico on 7/27/2015.
 */
public class CustomEditText extends EditText
{
    private AttributeSet attrs;

    public CustomEditText(Context context)
    {
        super(context);
        init(null);

        this.attrs = null;
    }

    public CustomEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);

        this.attrs = attrs;
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);

        this.attrs = attrs;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

        this.attrs = attrs;
    }

    /**
     * @param attrs
     */
    private void init(AttributeSet attrs)
    {
        if (attrs!=null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);

            String fontName = a.getString(R.styleable.CustomTextView_fontName);

            if (fontName!=null)
            {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}
