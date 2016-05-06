package net.wiiala.helpfullib.interactionstructure;


import java.lang.reflect.Method;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Object will contain information about a valid return value a method has 
 * and can then later be checked from this class 
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 * @param <T>
 */
public class OperationValidBehavior<T> {  

    /**Allowed return values can be a complex object, a pattern expression, number etc.*/
    private final Object validValue;
    
    /**Comparision type to use*/
    private final COMPARISION comparator;
    
   
    /**
     * The comparative attributes
     */
    public enum COMPARISION{
        /** Equals return value*/
        EQUAL, 
        /** does not return null*/
        NOTNULL,
        /** return value is less or equal to entered value*/
        LESSOREQUAL , 
        /** return value is less than entered value*/
        LESS , 
        /** return value is greater or equal to entered value*/
        GREATEROREQUAL, 
        /** return value is greater than entered value*/
        GREATER,
        /** return value follows a string pattern that is mathed with entered string*/
        PATTERN,
        /** returns nothing (void)*/
        VOID;  
    }
    
    /**
     * Used when return type should be VOID 
     */
    public OperationValidBehavior(){
 
        this.validValue = null;
        this.comparator = COMPARISION.VOID;
    }
    
    /**
     * Used to add valid value method should be tested against
     * @param comparator
     * @param validValue 
     */
    public OperationValidBehavior(COMPARISION comparator,T validValue){
       this.comparator = comparator;
       this.validValue = validValue;
    }
    
    /**
     * Validate a method against rule set in this class
     * and throws exception if non valid 
     * @param method
     * @param returnValue
     * @throws MethodBehaviourException when method don't fit demands on it
     */
    public void validateMethod(Method method, Object returnValue) throws MethodBehaviourException {
        
        boolean comparableValues = true;
        Comparable comparisionValue = null;
        Comparable returningValue = null;
        try{
            comparisionValue = (Comparable) this.validValue;
            returningValue = (Comparable) returnValue;
        }catch(ClassCastException e){
            comparableValues = false;
        }
         
        switch(comparator) {
            
            case VOID : 
                        if(method.getReturnType() != Void.TYPE){
                            throw new MethodBehaviourException("Function returns: " + method.getReturnType() + " and not Void as stated ");
                        }   
                        break;
                        
            case NOTNULL:
                        if(returnValue == null){
                            throw new MethodBehaviourException("Function returns: " + null + " which is not allowed ");
                        }   
                        break;
                        
            case PATTERN:
                        try{
                           if(returnValue instanceof CharSequence){
                              throw new MethodBehaviourException("Return value of method is not of approriate type: " + returnValue.getClass() ); 
                           }
                           if(this.validValue instanceof String){
                               throw new MethodBehaviourException("RegexExpression is stored in wrong object type: " + returnValue.getClass() );   
                           }
                           if(!Pattern.matches((String) this.validValue, (String)returnValue)){
                               throw new MethodBehaviourException("Method does not match regex pattern ");
                           } 
                        }catch(PatternSyntaxException e){
                            throw new MethodBehaviourException("Invalid regex pattern stored in this object");
                        }
                        break;
            case EQUAL:
                       if(!this.validValue.equals(returnValue)){
                           throw new MethodBehaviourException("return value of method don't match object in rule");
                       }
                       break;
            
            
            case GREATER: 
                        if(!comparableValues){ throw new MethodBehaviourException("Classes can not be compared");}
                        
                        //Smaller or equal then returning exception
                        if( comparisionValue.compareTo(returningValue) >= 0 ){
                            throw new MethodBehaviourException("return value is not greater than value in rule");
                        }
                        break;
                        
            case GREATEROREQUAL:
                        if(!comparableValues){ throw new MethodBehaviourException("Classes can not be compared");}
                        
                        //Smaller then returning exception
                        if( comparisionValue.compareTo(returningValue) > 0 ){
                            throw new MethodBehaviourException("return value is not greater or equal to value in rule");
                        }
                        break;
            case LESS: 
                        if(!comparableValues){ throw new MethodBehaviourException("Classes can not be compared");}
                        
                        if( comparisionValue.compareTo(returningValue) <= 0 ){
                            throw new MethodBehaviourException("return value is not smaller to value in rule");
                        }
                        break;
            case LESSOREQUAL: 
                        if(!comparableValues){ throw new MethodBehaviourException("Classes can not be compared");}

                        if( comparisionValue.compareTo(returningValue) < 0 ){
                            throw new MethodBehaviourException("return value is not smaller or equal to value in rule");
                        }
                        break;
        }
    }
    
}
