package appjam.scheduler;

/**
 * Created by Thorpedoes on 4/18/2016.
 */
public class EndBeforeStartException extends Exception {
    public EndBeforeStartException() {}

    public EndBeforeStartException(String msg) {
        super(msg);
    }
}
