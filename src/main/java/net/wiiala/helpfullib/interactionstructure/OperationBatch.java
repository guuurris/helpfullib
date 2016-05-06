package net.wiiala.helpfullib.interactionstructure;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * This class gives the possibility to control the order 
 * in which operation will be called and thereby is putting rules and structure.
 * There is no transaction control from within this class that is up to user of 
 * this library to handle, 
 * probably by throwing an exception to stop the rest of execution, this class
 * is only suitable for reading information and not to update information as
 * there is no transaction
 * @author Gustav Wiiala
 */
public class OperationBatch extends LinkedList<OperationAction> implements InteractionStructure {

   
    /**
     * Adds one or several class operations to perform
     * @param operations
     * @return 
     */
    @Override
    public boolean add(OperationAction... operations){
        return super.addAll(Arrays.asList(operations));
    }
    
    /**
     * Executes operation in correct order
     */
    @Override
    public void run() throws MethodBehaviourException{
        try {  
            for(OperationAction op : this){
                op.invokeMethod(); 
            }     
        } catch(IllegalAccessException | IllegalArgumentException 
                | InvocationTargetException e){
               e.printStackTrace();
        }
    }
}
