package appjam.scheduler;

/**
 * Created by Thorpedoes on 4/14/2016.
 */
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class EventWriter {
    private File dir;
    private File outFile;
    private FileWriter writer;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apj_scheduler";

    private ArrayList<CalendarEvent> eventList = null;

    public EventWriter(ArrayList<CalendarEvent> events) {
        eventList = events;

        dir = new File(path);
        if (!dir.exists()) dir.mkdir();

        Log.d("EVENTWRITER", "Constructor");
        outFile = new File(dir, "events.txt");
        try {
            writer = new FileWriter(outFile);
        } catch (IOException e) {
            Log.d("IOException", "in writer");
            e.printStackTrace();
        }
    }

    public void writeToFile() throws IOException {
        Log.d("EVENTWRITER", "writeToFile()");
        for (CalendarEvent ce : eventList) {
            writer.write(ce.getTitle() + ';');
            writeEventTime(ce.getStartTime());
            writeEventTime(ce.getEndTime());
            writer.write(ce.getAlrmSound() + ";\n");
            writer.flush();
        }
        writer.close();
    }

    public void writeEventTime(Calendar cal) {
        try {
            writer.write(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + ':');
            writer.write(Integer.toString(cal.get(Calendar.MONTH)) + ':');
            writer.write(Integer.toString(cal.get(Calendar.YEAR)) + ':');
            writer.write(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ':');
            writer.write(Integer.toString(cal.get(Calendar.MINUTE)) + ';');
        } catch (IOException e) {
            Log.d("IOException", "writing Cal");
            e.printStackTrace();
        }
    }
}
