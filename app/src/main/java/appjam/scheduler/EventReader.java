

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
    private ArrayList<CalendarEvent> eventItems;

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

    public void readFromFile() throws IOException {
        String line = null;
        while ((line = buffer.readLine()) != null) {
            System.out.println(line);
        }
        buffer.close();
    }
}