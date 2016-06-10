package com.arashkhoeini.connectivitychecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionDetector {

    private Context context;

    private boolean showing = false;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public void showDialog(Activity activity) {

        AlertDialog.Builder builder  = new AlertDialog.Builder(activity).
                setMessage("برای استفاده از این اپلیکیشن باید حتما به اینترنت متصل باشید")
                .setCancelable(false)
                .setPositiveButton("تلاش محدد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(!isConnectingToInternet()){
                    alert.show();
                }
            }
        });

        alert.show();

    }
}
