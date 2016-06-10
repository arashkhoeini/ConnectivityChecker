package com.arashkhoeini.connectivitychecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by arash on 6/7/16.
 * This the main class of this library.
 * You should instantiate this class and use doIfConnected method to do you network task. This method checks for
 * Internet connection. If the device was connected to the Internet performs the task. Else it will show an alert dialog.
 * Alert dialog is built in constructor. This class take two kind of constructor. The first constructor just takes
 * Context and Activity and builds dialog with default texts for alert message, retry button, and setting button
 * The second constructor takes three additional arguments for texts.
 */
public class ConnectivityChecker {
    private AlertDialog dialog;
    private AlertDialog customDialog;
    private Context context;
    private boolean cancelable = true;
    boolean retrying;
    private Doable task;


    /**
     * First constructor. It just takes Context and Activity and builds the dialog with default texts.
     * if you want to change texts for alert message, retry button and network button, you need to use the second
     * constructor.
     * @param context
     * @param activity
     */
    public ConnectivityChecker(final Context context,final Activity activity) {
        this.context = context;
        retrying = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).
                setMessage("You Are disconnected from Internet")
                .setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog2, int which) {
                        dialog.cancel();
                        retrying = true;
                    }
                }).setNegativeButton("Network Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        activity.startActivity(intent);
                    }
                });


        this.dialog = builder.create();

        final ConnectionDetector cd = new ConnectionDetector(context);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog2) {
                if (retrying) {
                    if (!cd.isConnectingToInternet()) {
                        dialog.show();
                    }else{
                        dialog.cancel();
                        task.doTask();
                        retrying = false;
                    }
                }
            }
        });


    }

    /**
     * Second constructor. You need to use this when you want to initialize the alert dialog with you own texts for
     * retry button and setting button.
     * @param context
     * @param activity
     * @param dialogRetryText
     * @param dialogSettingText
     * @param dialogAlertMessage
     */
    public ConnectivityChecker(final Context context, final Activity activity, String dialogAlertMessage, String dialogRetryText, String dialogSettingText) {
        this.context = context;
        retrying = false;
        if (dialogAlertMessage == null || dialogAlertMessage.isEmpty()) {
            dialogAlertMessage = "You Are disconnected from Internet";
        }
        if(dialogRetryText == null || dialogRetryText.isEmpty()){
            dialogRetryText = "Retry";
        }
        if(dialogSettingText == null || dialogSettingText.isEmpty()){
            dialogSettingText = "Setting";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).
                setMessage(dialogAlertMessage)
                .setCancelable(true)
                .setPositiveButton(dialogRetryText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        retrying = true;
                    }
                }).setNegativeButton(dialogSettingText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        activity.startActivity(intent);
                    }
                });
        this.dialog = builder.create();

        final ConnectionDetector cd = new ConnectionDetector(context);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog2) {
                if (retrying) {
                    if (!cd.isConnectingToInternet()) {
                        dialog.show();
                        retrying = false;
                    }else{
                        dialog.cancel();
                        task.doTask();
                        retrying = false;
                    }
                }
            }
        });
    }

    /**
     * Alert Dialog is cancelable by default. You can change that by using this method.
     * @param cancelable
     */
    public void isDialogCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    /**
     * You can change the alert message by using this method.
     * @param msg
     */
    public void setDialogAlertMessage(String msg) {
        dialog.setMessage(msg);
    }

    /**
     * If you want to use a custom dialog for alert dialog, you can use this method for adding your custom dialog.
     * @param customDialog
     */
    public void setCustomDialog(AlertDialog customDialog) {
        this.customDialog = customDialog;
    }

    /**
     * This is the method which you need to use to perform you Internet tasks. this method takes on Doable object as
     * argument. You need to define you task in doTask method of that object.
     * This method checks for the Internet connection. if user is connected to Internet, it performs your task. If not,
     * it shows an alert dialog to user.
     * @param task
     */
    public void doIfConnected(Doable task) {
        this.task = task;
        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {
            task.doTask();
        } else {
            if (customDialog == null) {
                dialog.show();
            } else {
                customDialog.show();
            }
        }
    }
}
