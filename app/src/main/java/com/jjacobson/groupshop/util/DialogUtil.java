package com.jjacobson.groupshop.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Jeremiah on 9/5/2017.
 */

public class DialogUtil {

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        return dialog;
    }
}
