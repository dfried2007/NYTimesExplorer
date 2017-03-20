package com.example.dfrie.nytexplore.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dfrie.nytexplore.R;
import com.example.dfrie.nytexplore.enums.SortOrderEnum;
import com.example.dfrie.nytexplore.fragments.DatePickerFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import static com.example.dfrie.nytexplore.fragments.DatePickerFragment.ARG_TAG;
import static com.example.dfrie.nytexplore.fragments.DatePickerFragment.D_TAG;


public class SetupSearchActivity extends AppCompatActivity {

    public static final String SETUPS_FILENAME = "setups.txt";

    private EditText etBeginDate;
    private EditText etEndDate;
    private CheckBox cbArts;
    private CheckBox cbFashion;
    private CheckBox cbSports;
    private CheckBox cbHighlight;
    private Spinner spSort;
    private Button butBeginDate;
    private Button butEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_search);

        // Fetch views
        etBeginDate = (EditText) findViewById(R.id.etBeginDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbFashion = (CheckBox) findViewById(R.id.cbFashion);
        cbSports = (CheckBox) findViewById(R.id.cbSports);
        spSort = (Spinner) findViewById(R.id.spSort);
        cbHighlight = (CheckBox) findViewById(R.id.cbHighlight);
        butBeginDate = (Button) findViewById(R.id.butBeginDate);
        butEndDate = (Button) findViewById(R.id.butEndDate);

        spSort.setAdapter(new ArrayAdapter<SortOrderEnum>(this,
                android.R.layout.simple_spinner_item, SortOrderEnum.values()));

        readItems();
    }

    @Override
    protected void onDestroy() {
        writeItems();
        super.onDestroy();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bund = new Bundle();
        bund.putInt(ARG_TAG, (v==butBeginDate? 0: 1));
        newFragment.setArguments(bund);
        newFragment.show(getFragmentManager(), D_TAG);
    }

    private void readItems() {
        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream(new File(getFilesDir(), SETUPS_FILENAME));
            props.load(is);
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        String strBeginDate = props.getProperty("etBeginDate");
        if (strBeginDate!=null) {
            etBeginDate.setText(strBeginDate);
        }
        String strEndDate = props.getProperty("etEndDate");
        if (strEndDate!=null) {
            etEndDate.setText(strEndDate);
        }
        String strArts = props.getProperty("cbArts");
        if (strArts!=null) {
            cbArts.setChecked(strArts.equals("1"));
        }
        String strSports = props.getProperty("cbSports");
        if (strSports!=null) {
            cbSports.setChecked(strSports.equals("1"));
        }
        String strFashion = props.getProperty("cbFashion");
        if (strFashion!=null) {
            cbFashion.setChecked(strFashion.equals("1"));
        }
        String strHighlight = props.getProperty("cbHighlight");
        if (strHighlight!=null) {
            cbHighlight.setChecked(strHighlight.equals("1"));
        }
        String strSort = props.getProperty("spSort");
        if (strSort!=null) {
            spSort.setSelection(SortOrderEnum.getEnumFor(strSort).getValue());
        }
    }

    private void writeItems() {
        Properties props = new Properties();
        try {
            props.setProperty("etBeginDate", etBeginDate.getText().toString());
            props.setProperty("etEndDate", etEndDate.getText().toString());
            props.setProperty("cbArts", cbArts.isChecked()? "1": "0");
            props.setProperty("cbSports", cbSports.isChecked()? "1": "0");
            props.setProperty("cbFashion", cbFashion.isChecked()? "1": "0");
            props.setProperty("cbHighlight", cbHighlight.isChecked()? "1": "0");
            props.setProperty("spSort", ((SortOrderEnum)spSort.getSelectedItem()).getName());

            File f = new File(getFilesDir(), SETUPS_FILENAME);
            OutputStream out = new FileOutputStream(f);
            props.store(out, "#Saved search setup params");
        } catch (Exception e ) {
            Toast.makeText(this, getString(R.string.cannot_write_file) + SETUPS_FILENAME, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
