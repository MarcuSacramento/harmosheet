// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.harmonica;

import java.io.FileFilter;
import java.io.IOException;
import java.io.File;
import harmotab.harmonica.HarmonicaModel;

public abstract class HarmonicaModelReader
{
    public static String HARMOTAB_3_MODEL_FILE_EXTENSION;
    public static String HARMOTAB_2_MODEL_FILE_EXTENSION;
    protected HarmonicaModel m_model;
    
    static {
        HarmonicaModelReader.HARMOTAB_3_MODEL_FILE_EXTENSION = ".hmd";
        HarmonicaModelReader.HARMOTAB_2_MODEL_FILE_EXTENSION = ".md";
    }
    
    public static HarmonicaModelReader createReader(final HarmonicaModel model, final File file) {
        if (file.getName().endsWith(HarmonicaModelReader.HARMOTAB_2_MODEL_FILE_EXTENSION)) {
            return new HarmoTab2HarmonicaModelReader(model);
        }
        return new HarmoTab3HarmonicaModelReader(model);
    }
    
    public abstract void read(final File p0) throws IOException;
    
    public HarmonicaModelReader(final HarmonicaModel model) {
        this.m_model = null;
        this.m_model = model;
    }
    
    public static class ReadableModelFileFilter implements FileFilter
    {
        @Override
        public boolean accept(final File file) {
            final String name = file.getName();
            return name.endsWith(HarmonicaModelReader.HARMOTAB_3_MODEL_FILE_EXTENSION) || name.endsWith(HarmonicaModelReader.HARMOTAB_2_MODEL_FILE_EXTENSION);
        }
    }
}
