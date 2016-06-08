package net.wiiala.helpfullib.tuple;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a Java representation of a tuple where each element of it has a strong type
 * and it is best used as a return value for methods where no bigger need of creating 
 * specific class is in mind.
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 * @param <T>
 */
public class Tuple <T> {
    
    private Map<String,TupleElement<String,Object>> tuples;
    private T tupleType;
    private Class tupleClass;
 
    /**
     * Define a tuple through an array of elements that is of class T 
     * @param tupleClass a class that this instance should be of
     * @param elements elements of tuple
     * @throws InvalidTupleException when same identifier occur in several tuple-elements
     * @throws InstantiationException when class T can't be instantiated
     * @throws IllegalAccessException when this class is not allowed to instantiated object of type T
     */
    public Tuple(Class<T> tupleClass , TupleElement... elements) throws InvalidTupleException , InstantiationException, IllegalAccessException {
        this(tupleClass, Arrays.asList(elements));
    }
    
    /**
     * Define a tuple through a list of elements that is of class T 
     * @param tupleClass a class that this instance should be of
     * @param elements elements of tuple
     * @throws InvalidTupleException when same identifier occur in several tuple-elements
     * @throws InstantiationException when class T can't be instantiated
     * @throws IllegalAccessException when this class is not allowed to instantiated object of type T
     */
    public Tuple (Class<T> tupleClass, List<TupleElement> elements) throws InvalidTupleException, InstantiationException, IllegalAccessException {
        this(elements);
        this.tupleClass = tupleClass;
        this.tupleType = tupleClass.newInstance();
    }
    
    /**
     * Define a tuple through an array of elements
     * @param elements elements of tuple
     * @throws InvalidTupleException if same identifier occur in several tuple-elements
     */
    public Tuple (TupleElement... elements) throws InvalidTupleException {
        this(Arrays.asList(elements));
    }
    
    /**
     * Define a tuple through a list of elements
     * @param elements elements of tuple
     * @throws InvalidTupleException if same identifier occur in several tuple-elements
     */
    public Tuple(List<TupleElement> elements) throws InvalidTupleException {
        tuples = new HashMap();
        
        for(TupleElement<String, Object> element : elements) {
            //Add element and if element identifier is not unique; throw exception 
            if( tuples.put(element.identifier, element) != null){
                throw new InvalidTupleException();
            }
        }
    }
     
    /**
     * Define a tuple through a map 
     * @param type an instance of object it represent
     * @param elementMap tuple represented as map
     */
    public Tuple(T type, Map<String, TupleElement<String,Object>> elementMap){
        this.tuples = elementMap;
        this.tupleType = type;
        this.tupleClass = type.getClass();
    }
    
    /**
     * Finds all the identifiers in tuple
     * @return all identifier of tuple
     */
    public Collection<String> getIdentifiers() {
        return tuples.keySet();
    }
    
    /**
    * Get element based on identifier
    * @param identifier is the key for one tuple-element
    * @return one element or null if it don't exist
    */
    public TupleElement getElement(String identifier) {
        return tuples.get(identifier);
    }
    
    /**
     * Get tuple as collection  
     * @return a collection of all element in tuple
     */
    public Collection<TupleElement<String, Object>> getElements() {
        return tuples.values();
    }
    
    /**
     * Instance an object of this tuple on the type this class is of
     * @param identifiers
     * @return object of type T or null if T type is unknown
     * @throws ReflectiveOperationException
     * @throws IllegalArgumentException 
     */
    public T toObject(String ... identifiers) throws ReflectiveOperationException,
                                                     IllegalArgumentException { 
        //When tuple type is uncertain
        if(this.tupleType == null) {
            return null;
        }
        
        List <Class> constructorClasses  = new ArrayList();  //To store class types to use in constructor 
        List <Object> constructorValues  = new ArrayList();  //To store actual values to constructor initialisation
        
        //Retrive tuple's that match constructor in identical order
        for(String id : identifiers) {
            Object value = getElement(id).value;
            constructorClasses.add( value.getClass() );
            constructorValues.add( value ); 
        }
        
        //Create a constructor reference to object of type T (exception is thrown if it can't)
        Constructor<T> cons = tupleClass.getDeclaredConstructor(constructorClasses.toArray(new Class[constructorClasses.size()]));
       
        //return a new instance of specific variable
        return  cons.newInstance(constructorValues.toArray());
    }
    
    /**
     * Gives a string representation of this Tuple
     * @return A JSON style output of tuple in string format
     */
    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append("{");
        for(  TupleElement element  : getElements() ) {
            
            //Add comma after each element
            if(strb.length() > 1) {
               strb.append(",");
            }
            //Add element to string build
            strb.append(String.format(" %s : %s " , element.identifier, element.value.toString()) );    
        } 
        strb.append("}");
        
        return strb.toString();
    }
    
}
