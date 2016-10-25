package com.example.prashanthmudhelli.downloadservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.URL;


public class DownloadActivity extends AppCompatActivity {
    private int[] viewId = {R.id.pdf1, R.id.pdf2, R.id.pdf3, R.id.pdf4, R.id.pdf5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
    }

    public void startDownload(View view) {
        Intent intent = new Intent(getBaseContext(), BackgroundDownload.class);
        EditText editView;
        //String pdfId = "";
        String[] urls = new String[5];

        for (int i = 0; i < 5; i++) {
            //pdfId = "pdf" +(i+1);
            //int pdfId = getResources().getIdentifier(pdfId, "id", "com.example.prashanthmudhelli.downloadservices");
            editView = (EditText) findViewById(viewId[i]);
            urls[i] = editView.getText().toString().trim();
        }
        intent.putExtra("urls", urls);
        startService(intent);
    }

    public void closeApp(View view) {
        finish();
    }
}