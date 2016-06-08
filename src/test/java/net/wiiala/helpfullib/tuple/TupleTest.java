package net.wiiala.helpfullib.tuple;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests tuple related things 
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 */
public class TupleTest {
   
    private SimpleClass simple;
    private List<TupleElement> elements;
    private Tuple<SimpleClass> tuple;
    
    public TupleTest() {
    }

    
    @Test
    public void testSimpleTuple() throws Exception {
        
      //Empty tuple contains no values
      Assert.assertEquals(true, new Tuple().getElements().isEmpty());
      
      //Tuple with single element has one TupleElement in collection 
      Assert.assertEquals(1, new Tuple(new TupleElement("MyID","MyValue")).getElements().size());
      
      //Stored value is what it is suppose to be
      Assert.assertEquals("MyValue", new Tuple( new TupleElement("MyID","MyValue")).getElement("MyID").value );
      
    }
    
    /**
     * Test of toObject method, of class Tuple.
     * @throws java.lang.Exception
     */
    @Test
    public void testToObject() throws Exception {
        elements = new ArrayList();
        elements.add(new TupleElement("test", "My first value"));
        elements.add(new TupleElement("second", "My secondValue value"));
        tuple = new Tuple(SimpleClass.class, elements);
        Assert.assertTrue( tuple.toObject("test","second") instanceof SimpleClass );
        Assert.assertNotEquals(null, tuple.toObject("test","second"));
    }
 
}
