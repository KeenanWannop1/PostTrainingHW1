package com.example.clientapp;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    protected IMyAidlInterface iMyAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectService();
    }
    private void connectService(){
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface((IBinder) service);
                Toast.makeText(getApplicationContext(),"Service Connected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                iMyAidlInterface = null;
                Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
            }
        };
        if(iMyAidlInterface == null){
            Intent intent = new Intent();
            intent.setAction("com.example.admin.posttraininghw1.IMyAidlInterface");
            intent.setComponent(new ComponentName("com.example.admin.posttraininghw1",
                    "com.example.admin.posttraininghw1.BoundService"));
            bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
        }
    }
}
