package net.wiiala.helpfullib.tuple;

/**
 * This exception indicate when a Tuple is illegally formated 
 * mainly so that identifiers occur several times in tuple
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 */
public class InvalidTupleException extends Exception{
    
    /**
     * To be used when multiple occurrence of the same identifier found in tuple
     */
    public InvalidTupleException(){
        super("Multiple occurrence of the same identifier found in tuple");
    }
}
