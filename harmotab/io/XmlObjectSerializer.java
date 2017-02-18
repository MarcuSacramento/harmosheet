// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io;

import org.w3c.dom.Document;

public class XmlObjectSerializer implements ObjectSerializer
{
    protected Document m_document;
    
    public XmlObjectSerializer(final Document document) {
        if (document == null) {
            throw new NullPointerException();
        }
        this.m_document = document;
    }
    
    @Override
    public SerializedObject createSerializedObject(final String objectName, final int hashCode) {
        final SerializedObject object = new XmlSerializedObject(this, objectName);
        object.setAttribute("id", "#" + Integer.toHexString(hashCode));
        return object;
    }
}
