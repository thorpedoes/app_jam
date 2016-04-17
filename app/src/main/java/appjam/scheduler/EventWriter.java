package appjam.scheduler;

/**
 * Created by Thorpedoes on 4/14/2016.
 */
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EventWriter {
    private File dir;
    private File outFile;
    private FileWriter writer;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apj_scheduler";

    public EventWriter() {
        dir = new File(path);
        if (!dir.exists()) dir.mkdir();

        outFile = new File(dir, "events.txt");
        try {
            writer = new FileWriter(outFile);
        } catch (IOException e) {
            Log.d("IOException", "in writer");
            e.printStackTrace();
        }
    }

    public void writeToFile(String msg) throws IOException {
        writer.write(msg);
        writer.flush();
        writer.close();
    }
}
