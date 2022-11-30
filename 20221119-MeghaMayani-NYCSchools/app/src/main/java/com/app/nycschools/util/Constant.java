package com.app.nycschools.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class Constant {

    // Method to check internet connection
    public static boolean checkInternetConnection(@NonNull Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return true;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // any change in toastMessage for error
    // invokes this method
    public static void runMeMessage(Context context, String message) {
        if (message != null)
            Toast.makeText(context, message,
                            Toast.LENGTH_SHORT)
                    .show();
    }
}