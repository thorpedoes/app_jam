/**
 * Created by Thorpedoes on 4/12/2016.
 */

package appjam.scheduler;

import android.os.Parcel;
import android.os.Parcelable;

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
        out.writeLong(startTime.getTimeInMillis());
        out.writeLong(endTime.getTimeInMillis());
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
        startTime = Calendar.getInstance();
        startTime.setTimeInMillis(in.readLong());
        endTime = Calendar.getInstance();
        endTime.setTimeInMillis(in.readLong());
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

    public boolean validEvent() throws EndBeforeStartException, BeforeTodayException, EmptyEventTitleException {
        if (title.isEmpty()) throw new EmptyEventTitleException("Can't have an empty event title");
        if (endTime.before(startTime)) throw new EndBeforeStartException("End date must be after start date");
        Calendar current = Calendar.getInstance();
        if (startTime.before(current) || endTime.before(current)) throw new BeforeTodayException("Event must start after current date");

        return true;
    }

    // For debugging
    public void printEventInfo() {
        System.out.print(title + " | ");
        System.out.print(Integer.toString(startTime.get(Calendar.DAY_OF_MONTH)) + ' ');
        System.out.print(Integer.toString(startTime.get(Calendar.MONTH)) + ' ');
        System.out.print(Integer.toString(startTime.get(Calendar.YEAR)) + ' ');
        System.out.print(Integer.toString(startTime.get(Calendar.HOUR_OF_DAY)) + ':');
        System.out.print(Integer.toString(startTime.get(Calendar.MINUTE)) + " | ");
        System.out.print(Integer.toString(endTime.get(Calendar.DAY_OF_MONTH)) + ' ');
        System.out.print(Integer.toString(endTime.get(Calendar.MONTH)) + ' ');
        System.out.print(Integer.toString(endTime.get(Calendar.YEAR)) + ' ');
        System.out.print(Integer.toString(endTime.get(Calendar.HOUR_OF_DAY)) + ':');
        System.out.print(Integer.toString(endTime.get(Calendar.MINUTE)) + " | ");
        System.out.print(alrmSound + '\n');
    }
}