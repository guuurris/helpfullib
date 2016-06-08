package net.wiiala.helpfullib.tuple;

/**
 * This class is only used for simple test cases of files in 'net.wiiala.helpfullib.tuple' package
 * @author Gustav Wiiala <wiiala.gustav@gmail.com>
 */
public class SimpleClass {
        public String value1;
        public String value2;
        
        public SimpleClass() {
            
        }
        
        public SimpleClass(String test, String second){
            this.value1 = test;
            this.value2 = second;
        }
        
        @Override
        public String toString(){
            return String.format("Test: %s , Second %s", value1, value2);
        }
    }