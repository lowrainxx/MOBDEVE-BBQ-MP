package com.mobdeve.barbeque_mco2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.Locale;

public class EditEventActivity extends AppCompatActivity {
    private int id, hour, minute;
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
        eventTimeText.setText("Time: " + CalendarUtils.formattedTime(time)); // CalendarUtils.formattedTime(time)
    }

    private void initWidgets() {
        eventNameEdit = findViewById(R.id.eventNameEdit);
        eventPeopleEdit = findViewById(R.id.eventPeopleEdit);
        eventDateText = findViewById(R.id.eventDateText);
        eventTimeText = findViewById(R.id.eventTimeText);
    }

    public void backToMonthly(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void sendEmail(View view) {
        String recipientList = eventPeopleEdit.getText().toString();
        String[] recipients = recipientList.split(","); // Divides string by ','

        String subject = "[BBQALENDAR] " + eventNameEdit.getText().toString();
        String message = "You are invited to an event! \n\n" +
                "What: " + eventNameEdit.getText().toString() +
                "\n" + eventDateText.getText().toString() + " \n" + eventTimeText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setData(Uri.parse("mailto:")); // Email clients
        intent.setType("message/rfc822"); // Email clients
        startActivity(Intent.createChooser(intent, "Choose an Email Client:"));
        saveEvent(view);
    }

    public void saveEvent(View view) {
        DBManager db = DBManager.instanceofDatabase(this);

        int id = Event.eventsList.size();
        String eventName = eventNameEdit.getText().toString();
        Event newEvent = new Event(id, eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);

        db.addEventToDB(newEvent);
        finish();
    }

    public void selectTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){
                hour = selectedHour;
                minute = selectedMinute;
                eventTimeText.setText("Time: " + String.format(Locale.getDefault(),"%02d:%02d", hour, minute));
            }
        };

        // SPINNER STYLE
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}