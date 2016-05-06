package net.wiiala.helpfullib.interactionstructure;

import java.util.Collection;

/**
 * This interface sets must have method for API that can receive a set of method
 * invocation that should follow a certain order. 
 * 
 * Implementing class should store some type of iterable collection using 
 * Collection as super-type, where we store OperationAction.
 * 
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 */
public interface InteractionStructure extends Collection<OperationAction> {
     
    /**
     * Adds one or several operations to perform
     * @param operations
     * @return 
     */
     public boolean add(OperationAction... operations);
     
     /**
     * Executes operation in correct order
     * @throws MethodBehaviourException
     */
     public void run() throws MethodBehaviourException;
}