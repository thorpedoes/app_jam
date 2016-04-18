/**
 * Created by Thorpedoes on 4/12/2016.
 */

package appjam.scheduler;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;

public class CalendarEvent implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CalendarEvent createFromParcel(Parcel source) {
            return new CalendarEvent(source);
        }
        public CalendarEvent[] newArray(int size) {
            return new CalendarEvent[size];
        }
    };

    private String title;
    private Calendar startTime;
    private Calendar endTime;
    private String alrmSound;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int ignored) {
        out.writeString(title);

        out.writeString(alrmSound);
    }

    // Constructors
    public CalendarEvent() {}

    public CalendarEvent(String eventTitle, Calendar start, Calendar end, String sound) {
        title = eventTitle;
        startTime = start;
        endTime = end;
        alrmSound = sound;
    }

    public CalendarEvent(Parcel in) {
        title = in.readString();

        alrmSound = in.readString();
    }

    // Getters
    public String getTitle() { return title; }
    public Calendar getStartTime() { return startTime; }
    public Calendar getEndTime() { return endTime; }
    public String getAlrmSound() { return alrmSound; }

    // Setters
    public void setTitle(String newTitle) { title = newTitle; }
    public void setStartTime(Calendar startCal) { startTime = startCal; }
    public void setEndTime(Calendar endCal) { endTime = endCal; }
    public void setAlrmSound(String newSound) { alrmSound = newSound; }
}