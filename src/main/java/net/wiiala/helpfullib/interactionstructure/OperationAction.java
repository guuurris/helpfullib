package net.wiiala.helpfullib.interactionstructure;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class helps with storing a operation to perform within a class
 * helpful to have if we would like a batch of defined operations in a 
 * structured matter
 * @author Gustav Wiiala
 * @param <T>
 */
public class OperationAction <T> {
    
    private T instance;
    private Class classType;
    private Method method;
    private ArrayList<Object> parameters;
    private List<OperationValidBehavior> rules;
    
    /**
     * Constructor that accepts an instanced object to work with
     * @param instance 
     */
    public OperationAction(T instance){
        this.instance = instance;
        this.classType = instance.getClass();
        this.parameters = new ArrayList();
        this.rules = new ArrayList();
    }
    
    /**
     * Constructor that accept class which has method that will be used
     * @param instanceClass
     * @throws IllegalAccessException
     * @throws InstantiationException 
     */
    public OperationAction(Class<T> instanceClass) throws IllegalAccessException , InstantiationException {
        this(instanceClass.newInstance());
    }
    
    /**
     * Constructor takes information about method and method-parameters that later can be invoked
     *
     * @param instance  instance of class where method is
     * @param methodName name of method
     * @param parameters none or many arguments to be used with method
     *  
     * @throws NoSuchMethodException if method don't exist
     * @throws ClassNotFoundException if class can be found
     */
    public OperationAction(T instance, String methodName,Object... parameters) throws NoSuchMethodException, ClassNotFoundException {
        //add all parameters to method used for operation
        this(instance);
        //this.parameters.addAll(Arrays.asList(parameters));
        this.setParameters(parameters);
        this.setMethod(methodName, parameters);
        
    }
    
    /**
     * Constructor takes information about method and method-parameters that later can be invoked
     * 
     * @param instanceClass
     * @param methodName
     * @param parameters
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException 
     */
    public OperationAction(Class<T> instanceClass, String methodName, 
                            Object... parameters) throws IllegalAccessException,                                               
                                                    InstantiationException,
                                                    NoSuchMethodException ,
                                                    ClassNotFoundException{
       this(instanceClass.newInstance(), methodName, parameters);
    }
    
    
    /**
     * Add rule that must be followed on return value of method
     * @param rule comparable object that must match
     */
    public void addRules(OperationValidBehavior rule){
        this.rules.add(rule);
    }
    
    /**
     * Sets rules that must be followed on return value of method
     * @param rules comparable object that must match
     */
    public void setRules(List<OperationValidBehavior> rules){
        this.rules  = rules;
    }
    
    /**
     * Sets method/operation that will be called with next invokeMethod call
     * @param methodName 
     * @param parameters  parameters of operation
     * @throws NoSuchMethodException 
     */
    public void setMethod(String methodName, Object... parameters) throws NoSuchMethodException{
        this.setParameters(parameters);
        this.setMethod(methodName);
    }
    
    
    /**
     * Set method that will be used
     * @param methodName
     * @throws NoSuchMethodException 
     */
    public void setMethod(String methodName) throws NoSuchMethodException{
        //figure out class type of input parameters
        Collection<Class<?>> classes = new ArrayList();
        for(Object obj : parameters.toArray()){
            classes.add(obj.getClass());
        }
       //Assign the exact method/operation to use in class
       this.method = classType.getDeclaredMethod(methodName, classes.toArray(new Class[0]));
    }
    
    /**
     * Define parameter to be used with method
     * @param parameters 
     */
    public void setParameters(Object... parameters){
        this.parameters.clear();//removes element i list
        this.parameters.addAll(Arrays.asList(parameters));
    }
    
    /**
     * Changes instanced class to use
     * @param instance 
     */
    public void setInstance(T instance){
        this.instance = instance;
        this.classType = instance.getClass();
    }
    
    
    /**
     * Invoke method on specified class and continue to use previous class 
     * @param classType
     * @param methodName
     * @param parameters
     * @return 
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException 
     * @throws MethodBehaviourException 
     */
    public Object invokeMethod(Class<T> classType, 
                                String methodName,
                                Object... parameters) 
                                throws IllegalAccessException, 
                                InvocationTargetException, 
                                NoSuchMethodException, 
                                InstantiationException,
                                MethodBehaviourException{
      
        T ins = this.instance;  
        setInstance(classType.newInstance());
        Object returnObject = invokeMethod(methodName, parameters);
        setInstance(ins);
        return returnObject;
    }
    
    /**
     * Invoke method with or without parameters to be used
     * @param methodName
     * @param parameters
     * @return 
     * @throws NoSuchMethodException 
     * @throws java.lang.IllegalAccessException 
     * @throws java.lang.reflect.InvocationTargetException 
     * @throws MethodBehaviourException 
     */
    public Object invokeMethod(String methodName,Object... parameters) 
                                                            throws NoSuchMethodException 
                                                            , IllegalAccessException, 
                                                            InvocationTargetException,
                                                            MethodBehaviourException {
        this.setParameters(parameters);
        this.setMethod(methodName);
        return this.invokeMethod();
    }
    
    /**
     * Invoke operation with given parameters from this class 
     * @return 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     * @throws MethodBehaviourException 
     */
    public  Object invokeMethod() throws IllegalAccessException,
                                        IllegalArgumentException,
                                        InvocationTargetException,
                                        MethodBehaviourException{
        
        Object returnValue =  this.method.invoke(instance, parameters.toArray());
        for(OperationValidBehavior rule : rules){
            rule.validateMethod(method, returnValue);
        }
        return returnValue;
    }
    
}
