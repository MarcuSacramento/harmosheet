// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.throwables;

import java.io.IOException;

public class ScoreIoException extends IOException
{
    private static final long serialVersionUID = 1L;
    protected String m_filePath;
    
    public ScoreIoException(final Throwable cause, final String path) {
        super(cause);
        this.m_filePath = "";
        this.setFilePath(path);
    }
    
    public String getFilePath() {
        return this.m_filePath;
    }
    
    public void setFilePath(final String path) {
        this.m_filePath = path;
    }
}
