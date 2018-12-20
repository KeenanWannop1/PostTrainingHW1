package com.example.admin.posttraininghw1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText etStudentId;
    EditText etStudentName;
    TextView tvLst;
    BoundService mBoundService;
    boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etStudentId = findViewById(R.id.etStudentId);
        etStudentName = findViewById(R.id.etStudentName);
        tvLst = findViewById(R.id.tvLst);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void addStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int id = Integer.parseInt(etStudentId.getText().toString());
        String name = etStudentName.getText().toString();
        Student student = new Student(id, name);
        dbHandler.addHandler(student);
        etStudentId.setText("");
        etStudentName.setText("");

    }

    public void findStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Student student = dbHandler.findHandler(etStudentName.getText().toString());
        if (student != null) {
            tvLst.setText(String.valueOf(student.getID()) + " " + student.getStudentName() + System.getProperty("line.separator"));
            etStudentId.setText("");
            etStudentName.setText("");
        } else {
            tvLst.setText("No Match Found");
            etStudentId.setText("");
            etStudentName.setText("");
        }
    }

    public void removeStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        boolean result = dbHandler.deleteHandler(Integer.parseInt(etStudentId.getText().toString()));
        if (result) {
            etStudentId.setText("");
            etStudentName.setText("");
            tvLst.setText("Record Deleted");
        } else
            etStudentId.setText("No Match Found");
    }
    public void updateStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        boolean result = dbHandler.updateHandler(Integer.parseInt(etStudentId.getText().toString()),
                etStudentName.getText().toString());

        if (result) {
            etStudentId.setText("");
            etStudentName.setText("");
            tvLst.setText("Record Updated");
        } else
            etStudentId.setText("No Match Found");

    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }
    };
}

