package com.example.rodri.letsgetout.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 7/16/2016.
 */
public class SetUpGoalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btSetTargetDate;
    private int day, month, year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_goal);

        btSetTargetDate = (Button) findViewById(R.id.btSetTargetDate);
        toolbar = (Toolbar) findViewById(R.id.toolbarSetUpGoal);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btSetTargetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yeaR, int monthOfYear, int dayOfMonth) {
                        day = dayOfMonth;
                        month = monthOfYear + 1;
                        year = yeaR;
                    }
                };

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(SetUpGoalActivity.this, R.style.AppTheme, dateSetListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

                datePickerDialog.show();

            }
        });

    }
}
