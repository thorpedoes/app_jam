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
        // eventList = reader.getData()
        eventList = getSampleEventList();
        if(eventList.size() == 0)
            showEmptyView();
        else
            showListView();
    }
    // on destroy pass ArrayLis to reader
    private void showEmptyView() {
        setContentView(R.layout.empty_main);
        registerAddButton();
    }

    private void showListView() {
        // load the events
        setContentView(R.layout.content_main);
        setDateText();
        registerAddButton();
        updateTableLayout();
    }

    private void setDateText() {
        TextView title = (TextView) findViewById(R.id.dateText);
        title.setText(currentDateString());
    }

    private void registerAddButton() {
        Button button = (Button) findViewById(R.id.addButton);
        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EventDetails.class);
                    startActivityForResult(intent, activityRequests.activityToInt("GET_EVENT_INFO"));
                }
            });
        }
    }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == activityRequests.activityToInt("GET_EVENT_INFO") && resultCode == RESULT_OK) {
       }
   }

    private void updateTableLayout() {
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        assert tl != null;

        tl.addView(getDivider());
        for(CalendarEvent ce : eventList) {
            TableRow row = new TableRow(this);
            TextView timeCol = getDefaultRowText();
            TextView tv = getDefaultRowText();
            tv.setText(ce.getTitle());
            timeCol.setText(String.format("%d:00PM", ce.getEventInfo().getHour()));

            LinearLayout ll = new LinearLayout(row.getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);

            ll.addView(timeCol);
            ll.addView(tv);

            row.addView(ll);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tl.addView(row);
            tl.addView(getDivider());
        }
    }

    private ArrayList<CalendarEvent> getSampleEventList() {
        ArrayList<CalendarEvent> result = new ArrayList<>();
        EventInfo testInfo = new EventInfo(1,2,3,1,2,0,2);
        CalendarEvent testCE = new CalendarEvent("Test", "Something", testInfo, false, 0, "cool music");
        CalendarEvent testCE1 = new CalendarEvent("Whatever", "SomethingElse", testInfo, false, 0, "cool music");
        result.add(testCE);
        result.add(testCE1);
        return result;
    }

    private String currentDateString() {
        return "Thursday, April 14th"; // obviously don't leave this hard coded
    }

    private int findRowPadding() {
        int paddingPixel = 25;
        float density = getResources().getDisplayMetrics().density;
        return (int)(paddingPixel * density);
    }

    private ImageView getDivider() {
        ImageView divider = new ImageView(this);
        divider.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 1));
        divider.setBackgroundColor(Color.BLACK);
        return divider;
    }

    private TextView getDefaultRowText() {
        TextView result = new TextView(this);
        int paddingDp = findRowPadding();
        result.setPadding(0, 0, paddingDp, 0);
        result.setTextSize(m_defaultTextSize);
        return result;
    }
}
