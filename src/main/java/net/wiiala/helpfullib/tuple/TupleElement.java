package net.wiiala.helpfullib.tuple;

/**
 * This is a representation of a tuple element that has specified type and identifier
 * It borrows some of  the philosophy from JSON where each element has 
 * an identifier but adds a strict type to the value
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 * @param <T> type of identifying key to use
 * @param <E> type of object element will store
 */
public class TupleElement <T,E> {
    public final T identifier;
    public final E value;
    
    /**
     * Create an element to be used in a tuple
     * @param identifier identifies element by T value
     * @param value actual object to be contained in element
     */
    public TupleElement(T identifier, E value) {
        this.identifier = identifier;
        this.value = value;   
    }
}
