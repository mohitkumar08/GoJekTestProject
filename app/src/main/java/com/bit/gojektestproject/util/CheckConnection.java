package com.bit.gojektestproject.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnection {

    private static CheckConnection connection;
    private static Context context;

    public static CheckConnection initialize(Context contextRef) {
        if (connection == null) {
            connection = new CheckConnection();

        }
        if (context == null) {
            context = contextRef;
        }
        return connection;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
