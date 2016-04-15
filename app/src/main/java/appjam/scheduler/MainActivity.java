package appjam.scheduler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CalendarEvent> eventList;
    private float m_defaultTextSize = 24f;
    private ActivityRequest activityRequests = new ActivityRequest();
    // hold what's selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList = getSampleEventList(); // eventList = reader.getData()
        showHealthBarScreen();
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

    private String currentDateString() {
        return "Thursday, April 14th"; // obviously don't leave this hard coded
    }

    private void showHealthBarScreen() {
        setContentView(R.layout.activity_main);
        for(CalendarEvent ce : eventList) {
            // load bar + icon representing ce if necessary
        }
        // draw add button, draw calender button, register to correct events
    }
}

