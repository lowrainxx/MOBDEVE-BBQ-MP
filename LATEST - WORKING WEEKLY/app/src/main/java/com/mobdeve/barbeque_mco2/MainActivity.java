package com.mobdeve.barbeque_mco2;


import static com.mobdeve.barbeque_mco2.CalendarUtils.daysInMonthArray;
import static com.mobdeve.barbeque_mco2.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    private TextView monthYear;
    private RecyclerView calendarRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widgets();
        CalendarUtils.selectedDate = LocalDate.now(); // selects the current date now
        setMonthView();
        loadFromDBtoMemory();
    }

    private void widgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYear = findViewById(R.id.currentMonthYearText);
    }

    private void loadFromDBtoMemory() {
        DBManager db = DBManager.instanceofDatabase(this);
        db.populateEventListArray();
    }
    private void setMonthView() {
        monthYear.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);

        // to have 7 columns in the recycler view
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonth(View view) {
        // to go back 1 month before current month
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonth(View view) {
        // to go to the next month after the current month
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date!=null){
            CalendarUtils.selectedDate = date;
            setMonthView();
        }

        startActivity(new Intent(this, EditEventActivity.class));
    }

    public void weeklyAction(View view) {

        startActivity(new Intent(this, WeekViewActivity.class));
    }
}