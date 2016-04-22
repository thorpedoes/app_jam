package appjam.scheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CalendarEvent> eventList = new ArrayList<>();
    private int m_checkInterval = 500; // 500 ms = .5 sec, check status every .5 seconds
    private Handler m_handler = new Handler();
    private ArrayList<BarControl> bars = new ArrayList<>();

    public static int GET_EVENT = 0;

    Runnable m_StatusChecker = new Runnable() {
        @Override
        public void run() {
            updateBars((TableLayout) findViewById(R.id.tableLayout));
            m_handler.postDelayed(m_StatusChecker, m_checkInterval);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventReader reader = new EventReader(getApplicationContext());
        try {
            eventList = reader.readFromFile();
            Log.d("EVENTREADER", "read");
        } catch (IOException e) {
            Log.d("IOException", "reader main");
            e.printStackTrace();
        }

        Log.d("Size", Integer.toString(eventList.size()));
        for (CalendarEvent ce : eventList) {
            ce.printEventInfo();
        }
        showHealthBarScreen();
    }

    @Override
    public void onWindowFocusChanged(boolean focus) {
        super.onWindowFocusChanged(focus);
        if (bars.size() != eventList.size())
            updateBarList();
        m_StatusChecker.run();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventWriter writer = new EventWriter(getApplicationContext(), eventList);
        try {
            writer.writeToFile();
            Log.d("EVENTWRTIER", "onPause");
        } catch (IOException e) {
            Log.d("IOException", "onPause writer");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == GET_EVENT) {
            CalendarEvent result = data.getParcelableExtra("newEvent");
            Log.d("RESULT", result.getIcon());
            eventList.add(result);
            Collections.sort(eventList);
            updateBarList();
            Log.d("ADDING RESULT", "THAT WORKED!! new size: " + Integer.toString(eventList.size()));
        } else {
            Log.d("ADDING RESULT", "that was no valid");
        }
    }

    private void showHealthBarScreen() {
        setContentView(R.layout.health_bar_screen);
        TextView v = (TextView) findViewById(R.id.titleText);
        v.setBackgroundResource(R.drawable.main_title_background);
        v.setGravity(Gravity.CENTER);

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        for (CalendarEvent ce : eventList) {
            bars.add(new BarControl(ce, tl, this));
        }

        ImageButton addButton = (ImageButton) findViewById(R.id.addEventButton);
        assert addButton != null;
        addButton.setBackgroundResource(R.drawable.add_event_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                startActivityForResult(intent, GET_EVENT);
            }
        });



        ImageButton calButton = (ImageButton) findViewById(R.id.calendarButton);
        assert calButton != null;
        calButton.setBackgroundResource(R.drawable.calendar_button);
        calButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListEventsActivity.class);
                intent.putParcelableArrayListExtra("eventList", eventList);
                startActivity(intent);
            }
        });



    }

    private void updateBars(TableLayout tl) {
        tl.removeAllViews();
        for (Iterator<BarControl> it = bars.iterator(); it.hasNext(); ) {
            BarControl current = it.next();
            if (current.finished()) {
                notifyFinished(current.getEvent());
                eventList.remove(current.getEvent());
                it.remove();
            } else {
                current.update(tl);
            }
        }
    }

    private void updateBarList() {
        bars.clear();
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        tl.removeAllViews();
        for (CalendarEvent ce : eventList) {
            bars.add(new BarControl(ce, tl, this));
        }
        updateBars(tl);
    }

    private void notifyFinished(CalendarEvent ce) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Event finished");
        mBuilder.setContentText(String.format("\"%s\" just finished", ce.getTitle()));
        Intent resultActivity = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultActivity);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());
    }
}

