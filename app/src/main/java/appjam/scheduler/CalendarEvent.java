/**
 * Created by Thorpedoes on 4/12/2016.
 */

package appjam.scheduler;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarEvent implements Parcelable, Comparable<CalendarEvent> {

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
    private String icon;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int ignored) {
        out.writeString(title);
        out.writeLong(startTime.getTimeInMillis());
        out.writeLong(endTime.getTimeInMillis());
        out.writeString(icon);
    }

    // Constructors
    public CalendarEvent() {}

    public CalendarEvent(String eventTitle, Calendar start, Calendar end, String pic) {
        title = eventTitle;
        startTime = start;
        endTime = end;
        icon = pic;
    }

    public CalendarEvent(Parcel in) {
        title = in.readString();
        startTime = Calendar.getInstance();
        startTime.setTimeInMillis(in.readLong());
        endTime = Calendar.getInstance();
        endTime.setTimeInMillis(in.readLong());
        icon = in.readString();
    }

    // Getters
    public String getTitle() { return title; }
    public Calendar getStartTime() { return startTime; }
    public Calendar getEndTime() { return endTime; }
    public String getIcon() { return icon; }

    // Setters
    public void setTitle(String newTitle) { title = newTitle; }
    public void setStartTime(Calendar startCal) { startTime = startCal; }
    public void setEndTime(Calendar endCal) { endTime = endCal; }
    public void setIcon(String newSound) { icon = newSound; }

    public boolean validEvent() throws EndBeforeStartException, BeforeTodayException, EmptyEventTitleException {
        if (title.isEmpty()) throw new EmptyEventTitleException("Can't have an empty event title");
        if (endTime.before(startTime)) throw new EndBeforeStartException("End date must be after start date");
        Calendar current = Calendar.getInstance();
        if (startTime.before(current) || endTime.before(current)) throw new BeforeTodayException("Event must start after current date and time");

        return true;
    }

    @Override
    public int compareTo(CalendarEvent ce) {
        return this.getEndTime().getTime().compareTo(ce.getEndTime().getTime());
    }

    // For debugging
    public void printEventInfo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
        System.out.print(title + " | ");
        System.out.print(dateFormat.format(startTime.getTime()) + " | ");
        System.out.print(dateFormat.format(endTime.getTime()) + " | ");
        System.out.print(icon + '\n');
    }
}