package appjam.scheduler;

/**
 * Created by Thorpedoes on 4/14/2016.
 */
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class EventWriter {
    private File dir;
    private File outFile;
    //private FileWriter writer;
    private OutputStreamWriter writer;
    //private String path;// = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apj_scheduler";
    private static String filename = "events.txt";
//    private static String dirPath = "/apj_scheduler/";

    private ArrayList<CalendarEvent> eventList = null;

    public EventWriter(Context context, ArrayList<CalendarEvent> events) {
        eventList = events;
        try {
            writer = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
        } catch (IOException e) {
            Log.e("Exception", "Fail to open file for writing: " + e.toString());
        }

        //dir = new File(path);
        //if (!dir.exists()) dir.mkdir();

        Log.d("EVENTWRITER", "Constructor");
        //outFile = new File(dir, "events.txt");
        //try {
        //    writer = new FileWriter(outFile);
        //} catch (IOException e) {
        //    Log.d("IOException", "in writer");
        //    e.printStackTrace();
        //}
    }

    public void writeToFile() throws IOException {
        Log.d("EVENTWRITER", "writeToFile()");
        try {
            for (CalendarEvent ce : eventList) {
                String currentString = "";
                //writer.write(ce.getTitle() + ';');
                currentString += ce.getTitle() + ';';
                //writeEventTime(ce.getStartTime());
                currentString += eventTimeToString(ce.getStartTime());
                //writeEventTime(ce.getEndTime());
                currentString += eventTimeToString(ce.getEndTime());
                //writer.write(ce.getAlrmSound() + ";\n");
                currentString += ce.getAlrmSound() + ";\n";
                writer.write(currentString);
            }
        } catch (IOException e) {
            Log.e("Exeption", "Could not write");
            throw e;
        } finally {
            writer.close();
        }
    }

   private String eventTimeToString(Calendar cal) {
       String result = "";
       result += (Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + ':');
       result += (Integer.toString(cal.get(Calendar.MONTH)) + ':');
       result += (Integer.toString(cal.get(Calendar.YEAR)) + ':');
       result += (Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ':');
       result += (Integer.toString(cal.get(Calendar.MINUTE)) + ';');
       return result;
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
