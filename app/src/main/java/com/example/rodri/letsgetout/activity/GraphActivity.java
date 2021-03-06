package com.example.rodri.letsgetout.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.adapter.MonthlyBalanceAdapter;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.MonthlyBalance;
import com.example.rodri.letsgetout.util.Util;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 8/2/2016.
 */
public class GraphActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listOfMonthlyBalances;

    private List<MonthlyBalance> monthlyBalances;
    private MonthlyBalanceAdapter adapter;
    private MyDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        listOfMonthlyBalances = (ListView) findViewById(R.id.listOfMonthlyBalances);
        toolbar = (Toolbar) findViewById(R.id.toolbarGraph);

        toolbar.setTitle(R.string.toolbar_monthly_balance_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataSource = new MyDataSource(this);
        try {
            dataSource.open();

            monthlyBalances = dataSource.getAllMonthlyBalances();
            adapter = new MonthlyBalanceAdapter(this, 0, monthlyBalances);
            listOfMonthlyBalances.setAdapter(adapter);

            dataSource.close();

        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }


    }

}
