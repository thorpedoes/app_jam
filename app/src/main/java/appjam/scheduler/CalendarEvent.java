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
    private Calendar cal;
    private int durHours;
    private int durMintues;
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

    public CalendarEvent(Parcel in) {
        title = in.readString();

        alrmSound = in.readString();
    }

    // Getters
    public String getTitle() { return title; }
    public Calendar getEventTime() { return cal; }
    public int getHours() { return hoursLong; }
    public int getMinutes() {}
    public String getAlrmSound() { return alrmSound; }

    // Setters
    public void setTitle(String newTitle) { title = newTitle; }
    public void setEventTime(Calendar newCal) { cal = newCal; }
    public void setHours(int durHours) { hoursLong = durHours; }
    public void setMinutes(int durMinutes) { minutesLong = durMinutes; }
    public void setAlrmSound(String newSound) { alrmSound = newSound; }
}