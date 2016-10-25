package com.example.prashanthmudhelli.datastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int prefCount = pref.getInt("BOOKS_COUNT", 0);
        String timeStamp = "";
        Log.d("PM", "" +prefCount);
        for(int i=1; i<=prefCount; i++) {
            timeStamp += "Shared Preference " +i +": " +pref.getString("TIME_" + i, "") +"\n";
        }

        DataController controller = new DataController(getApplicationContext());
        try {
            Cursor cursor = controller.retrieve(new String[]{DataController.COL4}, null);

            for (int i = 1; !cursor.isLast(); i++) {
                cursor.moveToNext();
                timeStamp += "SQLite " + i + ": " + cursor.getString(0) + "\n";
            }
        }
        catch (Exception e) {
            Log.d("PM", "Cannot fetch column as data is not created");
        }

        TextView timeStampView = (TextView) findViewById(R.id.timeStamps);
        timeStampView.setText(timeStamp);
        controller.close();
    }

    public void addPref(View view) {
        Intent intent = new Intent(this, SharedPreferenceActivity.class);
        startActivity(intent);
    }

    public void addDB(View view) {
        Intent intent = new Intent(this, SQLiteActivity.class);
        startActivity(intent);
    }

    public void viewPref(View view) {
        Intent intent = new Intent(this, ViewPrefActivity.class);
        startActivity(intent);
    }

    public void viewDB(View view) {
        Intent intent = new Intent(this, ViewDBActivity.class);
        startActivity(intent);
    }

    public void closeApp(View view) {
        finish();
    }
}
