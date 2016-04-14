package appjam.scheduler;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int numActivities = 0; // temp, later check against ArrayList<Event>().size()
    private ArrayAdapter<EventInfo> adapter;

    private ArrayList<EventInfo> eventList;
    private EventReader er = new EventReader();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(numActivities == 0)
            showEmptyView();
        else
            showListView();
        // EventReader er = new EventReader();
    }

    private void addEmptyMsg() {
        TextView tv = (TextView)findViewById(R.id.textView);
    }

    private void showEmptyView() {
        setContentView(R.layout.empty_main);
        registerAddButton();
    }

    private void showListView() {
        // load/fill events
        setContentView(R.layout.content_main);
        registerAddButton();
        adapter = new ArrayAdapter<EventInfo>(this, R.layout.content_main, eventList);
        adapter.notifyDataSetChanged();
    }

    private void registerAddButton() {
        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                // set intent.putExtra(key, val) based on what's selected
                startActivity(intent);
            }
        });
    }
}
