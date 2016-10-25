package com.example.prashanthmudhelli.datastorage;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_db);
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView nameView = (TextView) findViewById(R.id.dbBookName);
        TextView authorView = (TextView) findViewById(R.id.dbBookAuthor);
        TextView descView = (TextView) findViewById(R.id.dbDescription);
        DataController controller = new DataController(getApplicationContext());
        Cursor cursor = controller.retrieve(new String[] {DataController.COL1, DataController.COL2, DataController.COL3}, DataController.COL4 +" DESC");
        cursor.moveToNext();
        Log.d("PM","HI" +cursor.getString(0));
        nameView.setText(cursor.getString(0));
        authorView.setText(cursor.getString(1));
        descView.setText(cursor.getString(2));
        controller.close();
    }

    public void cancel(View view) {
        finish();
    }
}
