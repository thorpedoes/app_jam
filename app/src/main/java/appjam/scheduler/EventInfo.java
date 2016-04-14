/**
 * Created by Thorpedoes on 4/12/2016.
 */

package appjam.scheduler;

public class EventInfo {
    // Date & Time
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minutes;

    // Duration
    private int durHours;
    private int durMinutes;

    // Constructor
    public EventInfo(int newDay, int newMonth, int newYear, int startHour, int startMinute, int newDurHours, int newDurMinutes)
    {
        day = newDay;
        month = newMonth;
        year = newYear;
        hour = startHour;
        minutes = startMinute;
        durHours = newDurHours;
        durMinutes = newDurMinutes;
    }

    // Getters
    public int getDay() { return day; }
    public int getMonth(){ return month; }
    public int getYear() { return year; }
    public int getHour() { return hour; }
    public int getMinutes() { return minutes; }
    public int getDurHours() { return durHours; }
    public int getDurMinutes() { return durMinutes; }
}
