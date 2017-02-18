// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io;

import harmotab.core.HarmoTabObject;

public interface SerializedObject
{
    String getObjectType();
    
    boolean hasAttribute(final String p0);
    
    void setAttribute(final String p0, final String p1);
    
    String getAttribute(final String p0);
    
    void setElementAttribute(final String p0, final HarmoTabObject p1);
    
    HarmoTabObject getElementAttribute(final String p0);
    
    void addChild(final SerializedObject p0);
    
    int getChildsNumber();
    
    SerializedObject getChild(final int p0);
}
