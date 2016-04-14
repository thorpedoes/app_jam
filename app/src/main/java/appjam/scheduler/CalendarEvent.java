/**
 * Created by Thorpedoes on 4/12/2016.
 */

package appjam.scheduler;

import android.util.Log;

public class CalendarEvent {
    /**
     * Reformat repeatTrue so that it can account for multiple days of the week
     */
    private String title;
    private String description;
    private EventInfo eventInfo;
    private boolean flashActive;
    private int repeatTrue;
    private String alrmSound;

    // Constructor
    public CalendarEvent(String newTitle, String newDescription, EventInfo newDate, boolean flash, int repeat, String newSound) {
        title = newTitle;
        description = newDescription;
        eventInfo = newDate;
        flashActive = flash;
        repeatTrue = repeat;
        alrmSound = newSound;
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