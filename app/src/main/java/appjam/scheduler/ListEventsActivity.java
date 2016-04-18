package appjam.scheduler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ListEventsActivity extends AppCompatActivity {

    private ArrayList<CalendarEvent> m_eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        m_eventList = (ArrayList<CalendarEvent>) getIntent().getSerializableExtra("Events");
        addEvents();
    }

    private void addEvents() {
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        for(CalendarEvent ce : m_eventList) {
            TableRow row = new TableRow(tl.getContext());
            LinearLayout ll = new LinearLayout(row.getContext());

            TextView titleText = new TextView(ll.getContext());
            TextView startTimeText = new TextView(ll.getContext());
            TextView endTimeText =   new TextView(ll.getContext());
            titleText.setText(ce.getTitle());
            startTimeText.setText("1:00 PM"); // don't hardcode later
            endTimeText.setText("2:00 PM");

            LinearLayout.LayoutParams match = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(titleText, match);
            ll.addView(startTimeText, match);
            ll.addView(endTimeText, match);

            ImageView divider = new ImageView(tl.getContext());
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 1);
            divider.setLayoutParams(lp);
            divider.setBackgroundColor(Color.BLACK);

            row.addView(ll);

            tl.addView(row);
        }
    }

}
