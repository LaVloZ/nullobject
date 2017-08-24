
package lv.merrill.nullobject;


public class CannotNullifyException extends RuntimeException {
    
    
    private static final long serialVersionUID = -6638009558960223144L;
    
    
    
    
    public CannotNullifyException() {
    }
    
    
    
    
    public CannotNullifyException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
    
    public CannotNullifyException(String message, Throwable cause) {
        
        super(message, cause);
    }
    
    
    
    
    public CannotNullifyException(String message) {
        
        super(message);
    }
    
    
    
    
    public CannotNullifyException(Throwable cause) {
        
        super(cause);
    }
}
