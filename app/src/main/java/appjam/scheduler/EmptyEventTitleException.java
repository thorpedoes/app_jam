/**
 * Created by Thorpedoes on 4/18/2016.
 */
package appjam.scheduler;

public class EmptyEventTitleException extends Exception {
    public EmptyEventTitleException() {}

    public EmptyEventTitleException(String msg) {
        super(msg);
    }
}
