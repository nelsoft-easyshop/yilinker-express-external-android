/**
 * 
 */
package com.yilinker.expresspublic.modules.common.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yilinker.expresspublic.R;

/**
 * @author Arci.Malabanan
 *
 */
public class CustomTextView extends TextView
{

    private AttributeSet attrs;
    
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
        
        this.attrs = attrs;
    }
    

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        
        this.attrs = attrs;
    }
    /**
     * @param context
     */
    public CustomTextView(Context context)
    {
        super(context);
        init(null);
        
        this.attrs = null;
        
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
