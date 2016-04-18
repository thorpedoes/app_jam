package appjam.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
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
        //eventList = getSampleEventList();
        reader = new EventReader();
        try {
            eventList = reader.readFromFile();
            Log.d("EVENTREADER", "read");
        } catch (IOException e) {
            Log.d("IOException", "reader main");
            e.printStackTrace();
        }

        for (CalendarEvent ce : eventList) {
            ce.printEventInfo();
        }

        showHealthBarScreen();
    }

    @Override
    public void onWindowFocusChanged(boolean focus) {
        super.onWindowFocusChanged(focus);
        //updateBars((TableLayout) findViewById(R.id.tableLayout));
        m_StatusChecker.run();
    }
    // on destroy pass ArrayLis to reader
    @Override
    protected void onPause() {
        super.onPause();
        writer = new EventWriter(getSampleEventList());
        try {
            writer.writeToFile();
            Log.d("EVENTWRTIER", "onPause");
        } catch (IOException e) {
            Log.d("IOException", "onPause writer");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        writer = new EventWriter(getSampleEventList());
        try {
            writer.writeToFile();
            Log.d("EVENTWRTIER", "onStop");
        } catch (IOException e) {
            Log.d("IOException", "onStop writer");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
        }
    }

    private ArrayList<CalendarEvent> getSampleEventList() {
        ArrayList<CalendarEvent> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 30);
        CalendarEvent testCE = new CalendarEvent("Test", cal, cal, "cool music");
        CalendarEvent testCE1 = new CalendarEvent("Whatever", cal, cal, "cool music");
        result.add(testCE);
        result.add(testCE1);
        return result;
    }

    private void showHealthBarScreen() {
        setContentView(R.layout.health_bar_screen);

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        for(CalendarEvent ce : eventList) {
            //eventList.add(ce);
            bars.add(new BarControl(ce, tl));
        }

        Button addButton = (Button) findViewById(R.id.addEventButton);
        assert addButton != null;
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                startActivity(intent);
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
                it.remove();
            } else {
                current.update(tl);
            }
        }
    }

}

