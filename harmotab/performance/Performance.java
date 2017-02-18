// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import harmotab.throwables.NotImplementedError;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.core.undo.RestoreCommand;
import harmotab.harmonica.Harmonica;
import java.io.File;
import harmotab.core.HarmoTabObjectListener;
import harmotab.core.HarmoTabObject;

public class Performance extends HarmoTabObject implements HarmoTabObjectListener
{
    public static final String FILE_ATTR = "file";
    public static final String NAME_ATTR = "name";
    public static final String HARMONICA_ATTR = "harmonica";
    protected File m_file;
    protected String m_name;
    protected Harmonica m_harmonica;
    
    public Performance(final File file, final String name, final Harmonica harmonica) {
        this.setFile(file);
        this.setName(name);
        this.setHarmonica(harmonica);
    }
    
    public Performance() {
        this(null, "", new Harmonica());
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new PerformanceRestoreCommand(this);
    }
    
    public File getFile() {
        return this.m_file;
    }
    
    public void setFile(final File file) {
        this.m_file = file;
        this.fireObjectChanged("file");
    }
    
    public String getName() {
        return this.m_name;
    }
    
    public void setName(final String name) {
        this.m_name = name;
        this.fireObjectChanged("name");
    }
    
    public Harmonica getHarmonica() {
        return this.m_harmonica;
    }
    
    public void setHarmonica(final Harmonica harmonica) {
        if (this.m_harmonica != null) {
            this.m_harmonica.removeObjectListener(this);
        }
        (this.m_harmonica = harmonica).addObjectListener(this);
        this.fireObjectChanged("harmonica");
    }
    
    @Override
    public void onObjectChanged(final HarmoTabObjectEvent event) {
        if (event.getSource() instanceof Harmonica) {
            this.fireObjectChanged("harmonica");
        }
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        throw new NotImplementedError("Serialization not implemented for Performances");
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        throw new NotImplementedError("Serialization not implemented for Performances");
    }
}
