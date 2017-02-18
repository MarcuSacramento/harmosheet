// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.undo.RestoreCommand;

public class Tempo extends Element
{
    public static final String VALUE_ATTR = "value";
    public static final int MIN_TEMPO_VALUE = 10;
    public static final int MAX_TEMPO_VALUE = 500;
    public static final int DEFAULT_TEMPO_VALUE = 120;
    protected int m_tempo;
    
    public Tempo() {
        super((byte)14);
        this.setValue(120);
    }
    
    public Tempo(final int tempo) {
        super((byte)14);
        this.setValue(tempo);
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new TempoRestoreCommand(this);
    }
    
    public int getValue() {
        return this.m_tempo;
    }
    
    public void setValue(final int tempo) {
        if (tempo < 10 || tempo > 500) {
            throw new OutOfBoundsError("Bad tempo value '" + tempo + "'.");
        }
        this.m_tempo = tempo;
        this.fireObjectChanged("value");
    }
    
    public float getBeatPeriodInSeconds() {
        return 60.0f / this.m_tempo;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("value", new StringBuilder(String.valueOf(this.getValue())).toString());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setValue(Integer.parseInt(object.getAttribute("value")));
    }
}
