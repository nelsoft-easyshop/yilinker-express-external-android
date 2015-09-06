package com.yilinker.expresspublic.core.utilities;

import android.content.Context;

/**
 * Created by Jeico.
 */
public class CommonUtils
{
    public static int convertDpToPixels(Context context, int dp)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);

        return pixels;
    }
}
