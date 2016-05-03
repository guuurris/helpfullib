package net.wiiala.helpfullib.interactionstructure;

/**
 * Exception that occur when a method does not behave as expected
 * e.g. if return value is not in span of min and max value.
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 */
public class MethodBehaviourException extends Exception {

    public MethodBehaviourException(String message) {
        super("MethodBehaviourException occurred due to: " + message);
    }  
}
