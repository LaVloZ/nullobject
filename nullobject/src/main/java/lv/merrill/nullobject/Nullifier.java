
package lv.merrill.nullobject;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.mockito.Mockito;



class Nullifier {
    
    
    private Cache cache;
    
    
    
    
    Nullifier(Cache cache) {
        
        this.cache = cache;
    }
    
    
    
    
    public <T extends NullObject> T nullify(Class<T> clazz) {
        
        if (cache.contains(clazz)) {
            return cache.get(clazz);
        }
        else {
            return nullify0(clazz);
        }
    }
    
    
    
    
    private <T extends NullObject> T nullify0(Class<T> clazz) {
        
        T mock = Mockito.mock(clazz);
        Mockito.when(mock.isNull()).thenReturn(true);
        
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            handleReturnType(mock, m);
        }
        
        cache.add(clazz, mock);
        return mock;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    private <T extends NullObject> void handleReturnType(T mock,
            Method method) {
        
        try {
            Class<?> returnType = method.getReturnType();
            if (checkIfCannotBeMocked(method, returnType)) {
                return;
            }
            
            Object returnedMock = null;
            
            if (cache.contains(returnType)) {
                returnedMock = cache.get(returnType);
            }
            else if (NullObject.class.isAssignableFrom(returnType)) {
                returnedMock = nullify((Class<T>) returnType);
            }
            
            Object[] params = prepareParameters(method);
            Mockito.when(method.invoke(mock, params)).thenReturn(returnedMock);
        }
        catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            
            // Ignoré.
        }
    }
    
    
    
    
    private boolean checkIfCannotBeMocked(Method method, Class<?> returnType) {
        
        String methodName = method.getName();
        Class<?>[] parametersType = method.getParameterTypes();
        return isEquals(methodName, parametersType, returnType)
                || isHashCode(methodName, parametersType, returnType)
                || isWait(methodName, parametersType, returnType)
                || isNotifyOrNotifyAll(methodName, parametersType, returnType)
                || isGetClass(methodName, parametersType, returnType)
                || isToString(methodName, parametersType, returnType)
                || isIsNull(methodName, parametersType, returnType);
    }
    
    
    
    
    private boolean isEquals(String methodName, Class<?>[] parametersType,
            Class<?> returnType) {
        
        return "equals".equals(methodName) && parametersType.length == 1
                && parametersType[0].equals(Object.class)
                && returnType.equals(boolean.class);
    }
    
    
    
    
    private boolean isHashCode(String methodName, Class<?>[] parametersType,
            Class<?> returnType) {
        
        return "hashCode".equals(methodName) && parametersType.length == 0
                && returnType.equals(int.class);
    }
    
    
    
    
    private boolean isWait(String methodName, Class<?>[] parametersType,
            Class<?> returnType) {
        
        return "wait".equals(methodName)
                && handleWaitParametersType(parametersType)
                && returnType.equals(void.class);
    }
    
    
    
    
    private boolean handleWaitParametersType(Class<?>[] parametersType) {
        
        if (parametersType == null || parametersType.length == 0) {
            return true;
        }
        else if (parametersType.length == 1
                && parametersType[0].equals(long.class)) {
            return true;
        }
        else if (parametersType.length == 2
                && parametersType[0].equals(long.class)
                && parametersType[1].equals(int.class)) {
            return true;
        }
        
        return false;
    }
    
    
    
    
    private boolean isToString(String methodName, Class<?>[] parametersType,
            Class<?> returnType) {
        
        return "toString".equals(methodName) && parametersType.length == 0
                && returnType.equals(String.class);
    }
    
    
    
    
    private boolean isGetClass(String methodName, Class<?>[] parametersType,
            Class<?> returnType) {
        
        return "getClass".equals(methodName) && parametersType.length == 0
                && returnType.equals(Class.class);
    }
    
    
    
    
    private boolean isNotifyOrNotifyAll(String methodName,
            Class<?>[] parametersType, Class<?> returnType) {
        
        return ("notify".equals(methodName) || "notifyAll".equals(methodName))
                && parametersType.length == 0 && returnType.equals(void.class);
    }
    
    
    
    
    private boolean isIsNull(String methodName, Class<?>[] parametersType,
            Class<?> returnType) {
        
        return "isNull".equals(methodName) && parametersType.length == 0
                && returnType.equals(boolean.class);
    }
    
    
    
    
    private Object[] prepareParameters(Method method) {
        
        int count = method.getParameterCount();
        Object[] paramValues = new Object[count];
        for (int i = 0; i < count; i++) {
            paramValues[i] = Mockito.any();
        }
        
        return paramValues;
    }
}
