package com.example.prashanthmudhelli.datastorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteActivity extends AppCompatActivity {

    public int booksCount;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }

    @Override
    public void onResume() {
        super.onResume();
        DataController controller = new DataController(getApplicationContext());
        booksCount = (int) DatabaseUtils.queryNumEntries(controller.read(), controller.TABLE_NAME);
        Log.d("PM", "HI: " +booksCount);
    }

    public void dbSave(View view) {
        EditText nameView = (EditText) findViewById(R.id.dbBookName);
        String bookName = nameView.getText().toString();
        EditText authorView = (EditText) findViewById(R.id.dbBookAuthor);
        String bookAuthor = authorView.getText().toString();
        EditText descView = (EditText) findViewById(R.id.dbDescription);
        String bookDesc = descView.getText().toString();

        Context context = getApplicationContext();
        DataController controller = new DataController(context);

        Toast saveToast = Toast.makeText(context, "Book details are saved.", Toast.LENGTH_LONG);
        Toast emptyToast = Toast.makeText(context, "One or more fields are empty.", Toast.LENGTH_LONG);

        if(!bookName.isEmpty() && !bookAuthor.isEmpty() && !bookDesc.isEmpty()) {

            controller.insert(bookName, bookAuthor, bookDesc, dateFormat.format(new Date()));
            controller.close();
            saveToast.show();
            finish();
        }
        else {
            emptyToast.show();
            controller.close();
        }

    }

    public void cancel(View view) {
        finish();
    }
}
