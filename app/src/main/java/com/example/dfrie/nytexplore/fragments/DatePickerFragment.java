package com.example.dfrie.nytexplore.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.dfrie.nytexplore.R;
import com.example.dfrie.nytexplore.utils.NYTUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dfrie on 3/18/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public final static String D_TAG = "DatePicker_tag";
    public final static String ARG_TAG = "DatePicker_which";

    private int whichDate = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Bundle args = getArguments();
        whichDate = args.getInt(ARG_TAG);

        EditText editview;
        if (whichDate == 0) {
            editview = (EditText)getActivity().findViewById(R.id.etBeginDate);
        } else {
            editview = (EditText)getActivity().findViewById(R.id.etEndDate);
        }
        String strDate = editview.getText().toString();
        if (strDate.length() > 4) {
            Date date;
            try {
                date = NYTUtils.SDF.parse(strDate);
                if (strDate.equals(NYTUtils.SDF.format(date))) {
                    // It had a valid date, so...
                    year = date.getYear() + 1900;
                    month = date.getMonth();
                    day = date.getDate();
                }
            } catch (ParseException e) {
                // do nothing...
            }
        }
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Show the date chosen by the user...
        Log.d(D_TAG , "result date=" + year + "-" + month + "-" + day);
        EditText editview;
        if (whichDate == 0) {
            editview = (EditText) getActivity().findViewById(R.id.etBeginDate);
        } else {
            editview = (EditText)getActivity().findViewById(R.id.etEndDate);
        }
        final Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        editview.setText(NYTUtils.SDF.format(c.getTime()));
        Log.i(D_TAG , "result date.  text=" + NYTUtils.SDF.format(c.getTime()));
    }
}