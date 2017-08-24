
package lv.merrill.nullobject;


import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import org.apache.commons.collections4.IterableUtils;



public final class NullUtils {
    
    
    private NullUtils() {
    }
    
    
    
    
    private static final Cache CACHE;
    
    
    
    
    private static final Nullifier NULLIFIER;
    
    static {
        
        CACHE = new Cache();
        registerDefaultValues();
        NULLIFIER = new Nullifier(CACHE);
    }
    
    
    
    
    public static <T extends NullObject> T get(Class<T> clazz) {
        
        return NULLIFIER.nullify(clazz);
    }
    
    
    
    
    public static <T> void register(Class<? extends T> clazz, T nullValue) {
        
        CACHE.add(clazz, nullValue);
    }
    
    
    
    
    private static void registerDefaultValues() {
        
        register(boolean.class, Boolean.FALSE);
        register(short.class, (short) 0);
        register(char.class, '\0');
        register(int.class, 0);
        register(float.class, 0f);
        register(long.class, 0l);
        register(double.class, 0d);
        
        register(Boolean.class, Boolean.FALSE);
        register(Short.class, Short.valueOf((short) 0));
        register(Character.class, Character.valueOf('\0'));
        register(Integer.class, Integer.valueOf(0));
        register(Float.class, Float.valueOf(0f));
        register(Long.class, Long.valueOf(0l));
        register(Double.class, Double.valueOf(0d));
        
        register(String.class, "");
        
        register(Collection.class, Collections.emptyList());
        register(List.class, Collections.emptyList());
        register(Set.class, Collections.emptySet());
        register(NavigableSet.class, Collections.emptyNavigableSet());
        register(SortedSet.class, Collections.emptySortedSet());
        register(Map.class, Collections.emptyMap());
        register(NavigableMap.class, Collections.emptyNavigableMap());
        register(SortedMap.class, Collections.emptySortedMap());
        register(Enumeration.class, Collections.emptyEnumeration());
        
        register(Iterator.class, Collections.emptyIterator());
        register(ListIterator.class, Collections.emptyListIterator());
        register(Iterable.class, IterableUtils.emptyIterable());
    }
}
