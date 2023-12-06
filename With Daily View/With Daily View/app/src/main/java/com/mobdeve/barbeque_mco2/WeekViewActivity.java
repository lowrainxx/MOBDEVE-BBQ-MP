package com.mobdeve.barbeque_mco2;

import static com.mobdeve.barbeque_mco2.CalendarUtils.daysInMonthArray;
import static com.mobdeve.barbeque_mco2.CalendarUtils.daysInWeekArray;
import static com.mobdeve.barbeque_mco2.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYear;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        widgets();
        setWeekView();

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView eventNameEdit = view.findViewById(R.id.eventNameEdit);
//                TextView eventPeopleEdit = view.findViewById(R.id.eventPeopleEdit);
//                TextView eventDateText = view.findViewById(R.id.eventDateText);
//                TextView eventTimeText = view.findViewById(R.id.eventTimeText);
//
//                String eventName = eventNameEdit.getText().toString();
//                String eventPeople = eventPeopleEdit.getText().toString();
//                String eventDate = eventDateText.getText().toString();
//                String eventTime = eventTimeText.getText().toString();

                Intent edit_item = new Intent(getApplicationContext(), EditEventActivity.class);
//                edit_item.putExtra("name", eventName);
//                edit_item.putExtra("people", eventPeople);
//                edit_item.putExtra("date", eventDate);
//                edit_item.putExtra("time", eventTime);

                startActivity(edit_item);
            }
        }) ;
    }

    private void widgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYear = findViewById(R.id.currentMonthYearText);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView() {
        monthYear.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);

        // to have 7 columns in the recycler view
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }


    public void previousWeek(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeek(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEvent(View view) {

        startActivity(new Intent(this, EditEventActivity.class));
    }

    public void dailyAction(View view) {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }
}