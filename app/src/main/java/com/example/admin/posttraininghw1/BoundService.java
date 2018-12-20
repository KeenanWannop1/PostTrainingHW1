package com.example.admin.posttraininghw1;

import android.os.Binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

import java.util.List;

public class BoundService extends Service {
    private static String LOG_TAG = "BoundService";
    private IBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return new IMyAidlInterface.Stub() {
            /**
             * Implementation of the add() method
             */
            public List<String> loadStudentsFromOther() throws RemoteException {
                MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
                return dbHandler.loadHandler();
            }

        };
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
    }

    public class MyBinder extends Binder {
    BoundService getService() {
        return BoundService.this;
    }
}
}