package com.example.prashanthmudhelli.datastorage;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewPrefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pref);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int prefCount = pref.getInt("BOOKS_COUNT", 0);
        Log.d("PM", "" + prefCount);

        TextView nameView = (TextView) findViewById(R.id.prefBookName);
        TextView authorView = (TextView) findViewById(R.id.prefBookAuthor);
        TextView descView = (TextView) findViewById(R.id.prefDescription);

        nameView.setText(pref.getString("BOOK_NAME_" +prefCount, ""));
        authorView.setText(pref.getString("BOOK_AUTHOR_" +prefCount, ""));
        descView.setText(pref.getString("BOOK_DESC_" +prefCount, ""));
    }

    public void cancel(View view) {
        finish();
    }
}
