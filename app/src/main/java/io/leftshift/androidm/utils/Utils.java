package io.leftshift.androidm.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import io.leftshift.sample.R;

/**
 * Created by akshay on 5/12/15.
 */
public class Utils {

    public static void showSnackBar(final Context context, String message, View view) {
        if (context != null) {
            final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            snackbar.setAction(R.string.app_settings, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            TextView textView = (TextView) v.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(3);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }
}
