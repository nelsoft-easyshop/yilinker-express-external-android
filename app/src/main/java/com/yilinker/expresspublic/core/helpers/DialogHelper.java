package com.yilinker.expresspublic.core.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yilinker.expresspublic.R;

/**
 * Created by Jeico.
 */
public class DialogHelper
{
    public static AlertDialog createOkDialog(Context context, boolean isCancelable, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppAlertDialogTheme);
        if(title != null)
        {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setCancelable(isCancelable);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
