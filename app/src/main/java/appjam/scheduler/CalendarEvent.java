/**
 * Created by Thorpedoes on 4/12/2016.
 */

package appjam.scheduler;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CalendarEvent implements Parcelable {
    /**
     * Reformat repeatTrue so that it can account for multiple days of the week
     */

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CalendarEvent createFromParcel(Parcel source) {
            return new CalendarEvent(source);
        }
        public CalendarEvent[] newArray(int size) {
            return new CalendarEvent[size];
        }
    };

    private String title;
    private String description;
    private EventInfo eventInfo;
    private boolean flashActive;
    private int repeatTrue;
    private String alrmSound;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int ignored) {
        out.writeString(title);
        out.writeString(description);

        out.writeInt(repeatTrue);
        out.writeString(alrmSound);
    }



    // Constructor
    public CalendarEvent(String newTitle, String newDescription, EventInfo newDate, boolean flash, int repeat, String newSound) {
        title = newTitle;
        description = newDescription;
        eventInfo = newDate;
        flashActive = flash;
        repeatTrue = repeat;
        alrmSound = newSound;
    }

    public CalendarEvent(Parcel in) {
        title = in.readString();
        description = in.readString();

        repeatTrue = in.readInt();
        alrmSound = in.readString();
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public EventInfo getEventInfo() { return eventInfo; }
    public boolean getFlashActive() { return flashActive; }
    public int getRepeatTrue() { return repeatTrue; }
    public String getAlrmSound() { return alrmSound; }

    // Setters
    public void setTitle(String newTitle) { title = newTitle; }
    public void setDescription(String newDescription) { description = newDescription; }
    public void setEventInfo(EventInfo newEventInfo) { eventInfo = newEventInfo; }
    public void setFlash_active(boolean flash) { flashActive = flash; }
    public void setRepeatTrue(int repeat) { repeatTrue = repeat; }
    public void setAlrmSound(String newSound) { alrmSound = newSound; }
}