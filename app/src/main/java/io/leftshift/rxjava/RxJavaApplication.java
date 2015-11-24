package io.leftshift.rxjava;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by gandharva on 24/11/15.
 */
public class RxJavaApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
