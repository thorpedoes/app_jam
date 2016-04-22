

/**
 * Created by Thorpedoes on 4/13/2016.
 */

package appjam.scheduler;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Calendar;

public class EventReader {
    private ArrayList<CalendarEvent> eventList;

    private File inFile;
    private File dir;
    private FileReader reader;
    private BufferedReader buffer;
    private String path;
    private String filename = "events.txt";
    private String dirname = "/apj_scheduler";

    public EventReader(Context context)
    {
        File mainDir = context.getFilesDir();

        inFile = new File(mainDir, filename);
        if (inFile.exists()) {
            try {
                reader = new FileReader(inFile);
                buffer = new BufferedReader(reader);
                Log.d("CONSTRUCTOR", "reader");
            } catch (IOException e) {
                Log.d("IOException", "in reader");
                e.printStackTrace();
            }
        }
    }

    public ArrayList<CalendarEvent> readFromFile() throws IOException {
        eventList = new ArrayList<>();
        Log.d("EVENTREADER", "readFromFile()");
        if (inFile.exists()) {
            String line = null;
            while ((line = buffer.readLine()) != null) {
                Log.d("LINE", line);
                String[] tokens = line.split(";");
                try {
                    CalendarEvent event = makeEvent(tokens);
                    if (event.validEvent()) {
                        eventList.add(makeEvent(tokens));
                    }
                } catch (BeforeTodayException | EmptyEventTitleException | EndBeforeStartException e) {
                    Log.v("Invalid event", String.format("%s : invalid, nothing added", tokens));
                }
            }
            buffer.close();
        }

        return eventList;
    }

    private CalendarEvent makeEvent(String[] tokens) {
        CalendarEvent newEvent = new CalendarEvent();

        newEvent.setTitle(tokens[0]);
        newEvent.setStartTime(getTime(tokens[1].split(":")));
        newEvent.setEndTime(getTime(tokens[2].split(":")));
        newEvent.setIcon(tokens[3]);

        return newEvent;
    }

    private Calendar getTime(String[] calInfo) {
        Calendar newCal = Calendar.getInstance();
        newCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(calInfo[0]));
        newCal.set(Calendar.MONTH, Integer.parseInt(calInfo[1]));
        newCal.set(Calendar.YEAR, Integer.parseInt(calInfo[2]));
        newCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(calInfo[3]));
        newCal.set(Calendar.MINUTE, Integer.parseInt(calInfo[4]));
        return newCal;
    }
}