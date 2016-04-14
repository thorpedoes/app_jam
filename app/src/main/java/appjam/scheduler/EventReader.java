

/**
 * Created by Thorpedoes on 4/13/2016.
 */

package appjam.scheduler;

import android.util.Log;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class EventReader {
    private ArrayList<CalendarEvent> eventItems;
    private FileReader reader;
    private BufferedReader buffer;

    private String title;
    private String description;
    private EventInfo eventInfo;
    private boolean flashActive;
    private int repeatTrue;
    private String alrmSound;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public EventReader()
    {
        eventItems = new ArrayList<CalendarEvent>();
        Log.d("WHAT", "hey there");
//        reader = new FileReader("./file.txt");
        //buffer = new BufferedReader(reader);
    }

    //public ArrayList<CalendarEvent> getData()
    //{

    //}

}