package com.mobdeve.barbeque_mco2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class EditEventActivity extends AppCompatActivity {
    private EditText eventNameEdit, eventPeopleEdit;
    private TextView eventDateText, eventTimeText;
    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        initWidgets();
        time = LocalTime.now();
        eventDateText.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeText.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameEdit = findViewById(R.id.eventNameEdit);
        eventPeopleEdit = findViewById(R.id.eventPeopleEdit);
        eventDateText = findViewById(R.id.eventDateText);
        eventTimeText = findViewById(R.id.eventTimeText);
    }

    public void saveEvent(View view) {
        String eventName = eventNameEdit.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}