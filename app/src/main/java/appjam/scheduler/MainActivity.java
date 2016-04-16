package appjam.scheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CalendarEvent> eventList;
    private int m_checkInterval = 5000; // 5000 ms = 5 sec, check status every 5 seconds
    private Handler m_handler = new Handler();
    private ArrayList<BarControl> bars = new ArrayList<>();

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
        //eventList = getSampleEventList(); // eventList = reader.getData()
        showHealthBarScreen();
        m_StatusChecker.run();
    }

    // on destroy pass ArrayLis to reader


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
        }
    }

    private ArrayList<CalendarEvent> getSampleEventList() {
        ArrayList<CalendarEvent> result = new ArrayList<>();
        EventInfo testInfo = new EventInfo(1, 2, 3, 1, 2, 0, 2);
        CalendarEvent testCE = new CalendarEvent("Test", "Something", testInfo, false, 0, "cool music");
        CalendarEvent testCE1 = new CalendarEvent("Whatever", "SomethingElse", testInfo, false, 0, "cool music");
        result.add(testCE);
        result.add(testCE1);
        return result;
    }

    private void showHealthBarScreen() {
        setContentView(R.layout.health_bar_screen);

        for(CalendarEvent ce : getSampleEventList()) {
            bars.add(new BarControl(ce));
        }

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        updateBars(tl);

        Button addButton = (Button) findViewById(R.id.addEventButton);
        assert addButton != null;
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                startActivity(intent);
            }
        });

        Button calButton = (Button) findViewById(R.id.calendarButton);
        //calButton.setOnClickListener.... but I haven't written that activity yet
    }

    private void updateBars(TableLayout tl) {
        tl.removeAllViews();
        for(BarControl barControl : bars) {
            barControl.update(tl);
        }
    }

}

