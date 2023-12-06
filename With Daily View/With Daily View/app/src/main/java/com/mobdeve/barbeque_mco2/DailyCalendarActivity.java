package com.mobdeve.barbeque_mco2;

import static com.mobdeve.barbeque_mco2.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity {
    private TextView currentMonthText;
    private TextView currentDayText;
    private ListView hourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();
    }

    private void initWidgets() {
        currentMonthText = (TextView) findViewById(R.id.currentMonthText);
        currentDayText = (TextView) findViewById(R.id.currentDayText);
        hourListView = (ListView) findViewById(R.id.hourListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayView();
    }

    private void setDayView() {
        currentMonthText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String currentDay = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        currentDayText.setText(currentDay);
        setHourAdapter();
    }

    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList() {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour < 24; hour++) {
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsDateTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }
        return list;
    }

    public void previousDay(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDay(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }

    public void newEvent(View view) {
        startActivity(new Intent(this, EditEventActivity.class));
    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}