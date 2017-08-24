
package lv.merrill.nullobject;


/**
 * 
 * @author amansouri
 *
 */
public interface NullObject {
    
    
    public default boolean isNull() {
        
        return false;
    }
}
