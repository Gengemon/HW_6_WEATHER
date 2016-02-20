package com.hw_6_weather;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.util.Log;

public class Dialog {

    public static void showAlert(Context context,int rTitle, int rMessage){
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(context.getString(rTitle));
        dialog.setMessage(context.getString(rMessage));
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
                dialog.dismiss();
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();
    }

}
