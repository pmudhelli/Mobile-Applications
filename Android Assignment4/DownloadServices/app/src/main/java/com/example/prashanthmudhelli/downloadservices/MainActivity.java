package com.example.prashanthmudhelli.downloadservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pdfDownload(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void closeApp(View view) {
        finish();
    }
}