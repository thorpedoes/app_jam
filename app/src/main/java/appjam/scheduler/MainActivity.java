package appjam.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<EventInfo> eventList;
    private EventReader er = new EventReader();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int numActivities = 0; // temp, later check against ArrayList<Event>().size()
        if(numActivities == 0)
            showEmptyView();
        else
            showListView();
        // EventReader er = new EventReader();
    }

    private void showEmptyView() {
        setContentView(R.layout.empty_main);
        registerAddButton();
    }

    private void showListView() {
        // load/fill events
        ArrayAdapter<EventInfo> adapter = new ArrayAdapter<>(this, R.layout.content_main, eventList);
        setContentView(R.layout.content_main);
        registerAddButton();
        adapter.notifyDataSetChanged();
    }

    private void registerAddButton() {
        Button button = (Button) findViewById(R.id.addButton);
        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EventDetails.class);
                    // set intent.putExtra(key, val) based on what's selected
                    startActivity(intent);
                }
            });
        }
    }
}
