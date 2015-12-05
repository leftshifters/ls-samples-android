package io.leftshift.androidm.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by akshay on 5/12/15.
 */
public class BaseFragment extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void showPermissionDialog(Context context, String message, DialogInterface.OnClickListener
            okClickListener, DialogInterface.OnClickListener cancelOnClickListener) {
        new AlertDialog.Builder(context).setMessage(message).
                setCancelable(false).setPositiveButton("Ok", okClickListener).
                setNegativeButton("CANCEL", cancelOnClickListener)
                .show();
    }
}
