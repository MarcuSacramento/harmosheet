// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io;

public interface SerializableObject
{
    SerializedObject serialize(final ObjectSerializer p0);
    
    void deserialize(final ObjectSerializer p0, final SerializedObject p1);
}
