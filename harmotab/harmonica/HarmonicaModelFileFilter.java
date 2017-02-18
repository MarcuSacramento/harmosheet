// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.core.Localizer;
import rvt.util.gui.FileUtilities;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class HarmonicaModelFileFilter extends FileFilter
{
    public static final String HARMOTAB_2_HARMONICA_MODEL_EXTENSION = "md";
    public static final String HARMOTAB_3_HARMONICA_MODEL_EXTENSION = "hmd";
    private boolean m_acceptLegacyExtensions;
    
    public HarmonicaModelFileFilter() {
        this.m_acceptLegacyExtensions = false;
    }
    
    public HarmonicaModelFileFilter(final boolean acceptLegacyExtensions) {
        this.m_acceptLegacyExtensions = acceptLegacyExtensions;
    }
    
    public void setAcceptLegacyExtensions(final boolean accept) {
        this.m_acceptLegacyExtensions = accept;
    }
    
    public boolean acceptLegacyExtensions() {
        return this.m_acceptLegacyExtensions;
    }
    
    @Override
    public boolean accept(final File file) {
        return file != null && (file.isDirectory() || (FileUtilities.getExtension(file) != null && this.isHarmonicaModelExtension(FileUtilities.getExtension(file))));
    }
    
    @Override
    public String getDescription() {
        return Localizer.get("ET_HARMONICA_MODEL_FILE_DESC");
    }
    
    public boolean isHarmonicaModelExtension(final String extension) {
        return extension != null && ((this.acceptLegacyExtensions() && isHarmotab2HarmonicaModelExtension(extension)) || isHarmotab3HarmonicaModelExtension(extension));
    }
    
    public static boolean isHarmotab3HarmonicaModelExtension(final String extension) {
        return extension != null && extension.equals("hmd");
    }
    
    public static boolean isHarmotab2HarmonicaModelExtension(final String extension) {
        return extension != null && extension.equals("md");
    }
}
