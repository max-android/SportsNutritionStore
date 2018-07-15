package ru.exampleopit111.sportsnutritionstore.ui.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetInspector {

    private Context context;

    public NetInspector(Context context) {
        this.context = context;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
