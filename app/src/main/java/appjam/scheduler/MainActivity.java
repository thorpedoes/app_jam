package appjam.scheduler;

import android.app.Notification;
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
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CalendarEvent> eventList = new ArrayList<>();
    private int m_checkInterval = 500; // 500 ms = .5 sec, check status every .5 seconds
    private Handler m_handler = new Handler();
    private ArrayList<BarControl> bars = new ArrayList<>();
    private EventReader reader;
    private EventWriter writer;

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
        reader = new EventReader(getApplicationContext());
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
        if(bars.size() != eventList.size())
            updateBarList();
        m_StatusChecker.run();
    }
    @Override
    protected void onPause() {
        super.onPause();
        writer = new EventWriter(getApplicationContext(), getSampleEventList());
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
            CalendarEvent result = (CalendarEvent)data.getParcelableExtra("newEvent");
            eventList.add(result);
            updateBarList();
            Log.d("ADDING RESULT", "THAT WORKED!! new size: " + Integer.toString(eventList.size()));
        } else {
            Log.d("ADDING RESULT", "that was no valid");
        }
        if(resultCode == RESULT_OK) {
            //m_StatusChecker.run();
        }
    }

    private ArrayList<CalendarEvent> getSampleEventList() {
        ArrayList<CalendarEvent> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        cal.set(Calendar.HOUR_OF_DAY, 14);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.YEAR, 2016);
        end.set(Calendar.MONTH, Calendar.APRIL);
        end.set(Calendar.DAY_OF_MONTH, 19);
        end.set(Calendar.HOUR_OF_DAY, 14);
        end.set(Calendar.MINUTE, 52);

        CalendarEvent testCE = new CalendarEvent("Test", cal, end, "cool music");
        CalendarEvent testCE1 = new CalendarEvent("Whatever", cal, end, "cool music");
        result.add(testCE);
        result.add(testCE1);
        return result;
    }

    private void showHealthBarScreen() {
        setContentView(R.layout.health_bar_screen);

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        for(CalendarEvent ce : eventList) {
            bars.add(new BarControl(ce, tl));
        }

        Button addButton = (Button) findViewById(R.id.addEventButton);
        assert addButton != null;
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                startActivityForResult(intent, GET_EVENT);
            }
        });

        Button calButton = (Button) findViewById(R.id.calendarButton);
        assert calButton != null;
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
        for(Iterator<BarControl> it = bars.iterator(); it.hasNext();) {
            BarControl current = it.next();
            if(current.finished()) {
                notifyFinished(current.getEvent());
                it.remove();
            } else {
                current.update(tl);
            }
        }
    }

    private void updateBarList() {
        bars.clear();
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        for(CalendarEvent ce : eventList) {
            bars.add(new BarControl(ce, tl));
        }
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

    private String getTimeString(CalendarEvent ce) {
        SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-yyyy");
        return formatter.format(ce.getStartTime());
    }

}
