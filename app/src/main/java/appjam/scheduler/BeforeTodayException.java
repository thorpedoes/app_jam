package appjam.scheduler;

/**
 * Created by Thorpedoes on 4/18/2016.
 */
public class BeforeTodayException extends Exception {
    public BeforeTodayException() {}

    public BeforeTodayException(String msg) {
        super(msg);
    }
}
