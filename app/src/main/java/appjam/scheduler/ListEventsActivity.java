package appjam.scheduler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListEventsActivity extends AppCompatActivity {

    private ArrayList<CalendarEvent> m_eventList;

    @Override
    @SuppressWarnings("unchecked")
    // in line 25, m_eventList = ...getSerializableExtra(), get an unchecked warning, safe to suppress
    // as we know that the only seralizable we put in the Intent was the ArrayList<ce>
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        m_eventList = (ArrayList<CalendarEvent>)this.getIntent().getSerializableExtra("eventList");

        if(m_eventList != null) {
            addEvents();
        }
    }

    private void addEvents() {
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        assert tl != null;
        for(CalendarEvent ce : m_eventList) {
            TableRow row = new TableRow(tl.getContext());
            LinearLayout ll = new LinearLayout(row.getContext());

            TextView titleText = new TextView(ll.getContext());
            TextView timeText = new TextView(ll.getContext());

            SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy  h:mm a");
            titleText.setText(ce.getTitle());
            timeText.setText(dateFormat.format(ce.getStartTime().getTime()) + "        " + dateFormat.format(ce.getEndTime().getTime()) + '\n');

            LinearLayout.LayoutParams match = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(titleText, match);
            ll.addView(timeText, match);

            ImageView divider = new ImageView(tl.getContext());
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 1);
            divider.setLayoutParams(lp);
            divider.setBackgroundColor(Color.BLACK);

            row.addView(ll);

            tl.addView(row);
        }
    }

}
