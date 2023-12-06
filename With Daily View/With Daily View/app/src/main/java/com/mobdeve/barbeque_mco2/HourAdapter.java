package com.mobdeve.barbeque_mco2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends ArrayAdapter<HourEvent> {
    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents) {
        super(context, 0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        HourEvent event = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);
        }

        setHour(convertView, event.time);
        setEvents(convertView, event.events);

        return convertView;
    }

    private void setHour(View convertView, LocalTime time) {
        TextView timeText = convertView.findViewById(R.id.timeText);
        timeText.setText(CalendarUtils.formattedShortTime(time));
    }

    private void setEvents(View convertView, ArrayList<Event> events) {
        TextView eventNo1 = convertView.findViewById(R.id.eventNo1);
        TextView eventNo2 = convertView.findViewById(R.id.eventNo2);
        TextView eventNo3 = convertView.findViewById(R.id.eventNo3);

        // if there are no existing events, then it will be hidden
        if(events.size() == 0) {
            hideEvent(eventNo1);
            hideEvent(eventNo2);
            hideEvent(eventNo3);
        } else if(events.size() == 1)  {
            setEvent(eventNo1, events.get(0));
            hideEvent(eventNo2);
            hideEvent(eventNo3);
        } else if(events.size() == 2)  {
            setEvent(eventNo1, events.get(0));
            setEvent(eventNo2, events.get(1));
            hideEvent(eventNo3);
        } else if(events.size() == 2)  {
            setEvent(eventNo1, events.get(0));
            setEvent(eventNo2, events.get(1));
            setEvent(eventNo3, events.get(2));
        } else { // if there are more than 3 events
            setEvent(eventNo1, events.get(0));
            setEvent(eventNo2, events.get(1));
            eventNo3.setVisibility(View.VISIBLE);
            String moreEvents = String.valueOf(events.size()-2);
            moreEvents += " More Events";
            eventNo3.setText(moreEvents);
        }
    }

    private void setEvent(TextView textView, Event event) {
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
    }

    private void hideEvent(TextView text) {
        text.setVisibility(View.INVISIBLE);
    }

}
