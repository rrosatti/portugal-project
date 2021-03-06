package com.example.rodri.letsgetout.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.Expense;
import com.example.rodri.letsgetout.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 7/17/2016.
 */
public class NewExpenseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etName;
    private EditText etValue;
    private Button btSetDate;
    private Button btConfirm;
    private TextView txtNewExpenseTitle;

    private int day = 0, month = 0, year = 0;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        // Initialize Views
        toolbar = (Toolbar) findViewById(R.id.toolbarNewExpense);
        etName = (EditText) findViewById(R.id.etExpenseName);
        etValue = (EditText) findViewById(R.id.etExpenseValue);
        btSetDate = (Button) findViewById(R.id.newExpense_btSetDate);
        btConfirm = (Button) findViewById(R.id.newExpense_btConfirm);
        txtNewExpenseTitle = (TextView) findViewById(R.id.txtNewExpense);

        // Apply custom font type to the EditTexts, Buttons and TextViews
        setStyle();

        dataSource = new MyDataSource(this);

        toolbar.setTitle(R.string.toolbar_new_expense);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        // Create a "Calendar Dialog" in order to get the values for day, month and year
        btSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yeaR, int monthOfYear, int dayOfMonth) {
                        day = dayOfMonth;
                        month = monthOfYear + 1;
                        year = yeaR;
                        Toast.makeText(getApplicationContext(), day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
                    }
                };

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewExpenseActivity.this, R.style.AppTheme, dateSetListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etName.getText().toString();
                String value = etValue.getText().toString();

                // check if any field was left empty
                if (description.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_description_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (value.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_value_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (day == 0) {
                    Toast.makeText(getApplicationContext(), R.string.toast_set_a_date, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        dataSource.open();

                        Expense newExpense = dataSource.createExpense(description, Float.valueOf(value), day, month, year);

                        // Check if there is already an instance for MonthlyBalance with the given month and year
                        // If yes, than we just add the new expense to it
                        // Else, we create a new MonthlyBalance
                        if (dataSource.isThereAlreadyAMonthlyBalance(month, year)) {
                            dataSource.addExpenseToTheMonthlyBalance(month, year, Float.valueOf(value));
                        } else {
                            dataSource.createMonthlyBalance(month, year, Float.valueOf(value), 0, -Float.valueOf(value));
                        }
                        dataSource.close();
                        Toast.makeText(getApplicationContext(), R.string.toast_new_expense_created, Toast.LENGTH_SHORT).show();

                        // Return the new expense in order to refresh the list in the previous activity
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", newExpense);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();

                    } catch (Exception e) {
                        dataSource.close();
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void setStyle() {
        Util.setTypeFace(getApplicationContext(), etName, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), etValue, "Quicksand-Italic.otf");

        Util.setTypeFace(getApplicationContext(), btConfirm, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSetDate, "Quicksand.otf");

        Util.setTypeFace(getApplicationContext(), txtNewExpenseTitle, "Quicksand.otf");
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
