package com.yilinker.expresspublic.core.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jeico on 4/27/2015.
 */
public class DateUtils
{
    /**
     * TODO
     * @param inputDate
     * @return
     */
    public static String displayDateAsReadable(Date inputDate)
    {
        String outputDateStr = "";

        if(inputDate != null)
        {
            SimpleDateFormat sdf_output = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

            outputDateStr = sdf_output.format(inputDate);
        }

        return outputDateStr;
    }

    /**
     * TODO
     * @param inputDate
     * @return
     */
    public static String displayTimeAsReadable(Date inputDate)
    {
        String outputTimeStr = "";

        if(inputDate != null)
        {
            SimpleDateFormat sdf_output = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            outputTimeStr = sdf_output.format(inputDate);
        }

        return outputTimeStr;
    }
}
