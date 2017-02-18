// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.harmonica;

import java.io.IOException;
import harmotab.harmonica.HarmonicaModel;
import java.io.File;

public abstract class HarmonicaModelWriter
{
    protected File m_file;
    
    public static HarmonicaModelWriter createWriter(final File file) {
        return new HarmoTab3HarmonicaModelWriter(file);
    }
    
    public HarmonicaModelWriter(final File file) {
        this.m_file = null;
        this.m_file = file;
    }
    
    public abstract void writeFile(final HarmonicaModel p0) throws IOException;
}
