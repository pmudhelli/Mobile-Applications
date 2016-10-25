package com.example.prashanthmudhelli.downloadservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by prashanth.mudhelli on 3/13/16.
 */
public class DownloadBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("FILES_DOWNLOADED")) {
            if(intent.getIntExtra("downloadedBytes", 0) > 0) {
                Toast.makeText(context, "Files Download Complete.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context, "Download Failed.", Toast.LENGTH_LONG).show();
            }

        }
        else if(intent.getAction().equals("DOWNLOAD_PROGRESS")) {
            Log.d("PM", "" +intent.getIntExtra("percentComplete", 0));
            Toast.makeText(context, intent.getIntExtra("percentComplete", 0) +"% complete.", Toast.LENGTH_SHORT).show();
        }
    }
}