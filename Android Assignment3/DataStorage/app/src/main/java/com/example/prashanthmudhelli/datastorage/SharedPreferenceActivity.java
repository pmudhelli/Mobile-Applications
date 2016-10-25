package com.example.prashanthmudhelli.datastorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedPreferenceActivity extends AppCompatActivity {

    private int booksCount;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        booksCount = pref.getInt("BOOKS_COUNT", 0);
    }

    public void prefSave(View view) {
        EditText nameView = (EditText) findViewById(R.id.prefBookName);
        String bookName = nameView.getText().toString();
        EditText authorView = (EditText) findViewById(R.id.prefBookAuthor);
        String bookAuthor = authorView.getText().toString();
        EditText descView = (EditText) findViewById(R.id.prefDescription);
        String bookDesc = descView.getText().toString();

        Context context = getApplicationContext();
        Toast saveToast = Toast.makeText(context, "Book details are saved.", Toast.LENGTH_LONG);
        Toast emptyToast = Toast.makeText(context, "One or more fields are empty.", Toast.LENGTH_LONG);

        if(!bookName.isEmpty() && !bookAuthor.isEmpty() && !bookDesc.isEmpty()) {

            booksCount += 1;

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor=pref.edit();
            editor.putInt("BOOKS_COUNT", booksCount);
            editor.putString("BOOK_NAME_" + booksCount, bookName);
            editor.putString("BOOK_AUTHOR_" + booksCount, bookAuthor);
            editor.putString("BOOK_DESC_" + booksCount, bookDesc);
            editor.putString("TIME_" +booksCount, dateFormat.format(new Date()));
            editor.apply();

            saveToast.show();
            finish();
        }
        else {
            emptyToast.show();
        }
    }

    public void cancel(View view) {
        finish();
    }
}