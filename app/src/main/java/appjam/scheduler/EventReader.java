

/**
 * Created by Thorpedoes on 4/13/2016.
 */

package appjam.scheduler;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class EventReader {
    private ArrayList<CalendarEvent> eventList;

    private File inFile;
    private FileReader reader;
    private BufferedReader buffer;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apj_scheduler/events.txt";

    public EventReader()
    {
        inFile = new File(path);
        if (inFile.exists()) {
            try {
                reader = new FileReader(inFile);
                buffer = new BufferedReader(reader);
            } catch (IOException e) {
                Log.d("IOException", "in reader");
                e.printStackTrace();
            }
        }
    }

    public ArrayList<CalendarEvent> readFromFile() throws IOException {
        eventList = new ArrayList<>();
        String line = null;
        while ((line = buffer.readLine()) != null) {
            String[] tokens = line.split(";");
            eventList.add(makeEvent(tokens));
        }
        buffer.close();

        return eventList;
    }

    private CalendarEvent makeEvent(String[] tokens) {
        CalendarEvent newEvent = new CalendarEvent();
        return newEvent;
    }
}