// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import java.io.File;
import harmotab.core.Score;

public abstract class ScoreIO implements Cloneable
{
    protected Score m_score;
    protected File m_file;
    
    public ScoreIO(final Score score, final String path) {
        this.setScore(score);
        this.setFile(new File(path));
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void setFile(final File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        this.m_file = file;
    }
    
    public File getFile() {
        return this.m_file;
    }
    
    public void setScore(final Score score) {
        if (score == null) {
            throw new NullPointerException();
        }
        this.m_score = score;
    }
    
    public Score getScore() {
        return this.m_score;
    }
}
