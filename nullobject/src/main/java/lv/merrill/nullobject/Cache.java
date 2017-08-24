
package lv.merrill.nullobject;


import java.util.HashMap;
import java.util.Map;



class Cache {
    
    
    private Map<Class<?>, Object> nulls = new HashMap<>();
    
    
    
    
    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> clazz) {
        
        return (T) nulls.get(clazz);
    }
    
    
    
    
    public boolean contains(Class<?> clazz) {
        
        return nulls.containsKey(clazz);
    }
    
    
    
    
    public <T> void add(Class<? extends T> clazz, T object) {
        
        nulls.put(clazz, object);
    }
}
