package com.example.prashanthmudhelli.trackpack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by prashanth.mudhelli on 4/4/16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //selectedDate.set(year, month, day);
        //dateView.setText("" +dateFormat.format(selectedDate.getTime()), TextView.BufferType.EDITABLE);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar selectedDate = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        EditText dateView = (EditText) getActivity().findViewById(HomeActivity.currentDatePicker);
        selectedDate.set(year, month, day);
        Log.d("PM", "" + dateFormat.format(selectedDate.getTime()));
        dateView.setText("" +dateFormat.format(selectedDate.getTime()), TextView.BufferType.EDITABLE);
    }
}