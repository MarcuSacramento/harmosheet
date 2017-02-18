// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.throwables;

public class FileFormatException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public FileFormatException(final String message) {
        super(message);
    }
    
    public FileFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
