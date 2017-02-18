// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.element.ElementFactory;
import harmotab.harmonica.TabModel;
import harmotab.harmonica.Harmonica;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;

public class HarmoTabObjectFactory
{
    public static HarmoTabObject create(final ObjectSerializer serializer, final SerializedObject object) {
        HarmoTabObject result = null;
        final String type = object.getObjectType();
        if (type.equals("#text")) {
            return null;
        }
        if (type.equals("duration")) {
            result = new Duration();
        }
        else if (type.equals("figure")) {
            result = new Figure();
        }
        else if (type.equals("height")) {
            result = new Height();
        }
        else if (type.equals("effect")) {
            result = new Effect();
        }
        else if (type.equals("repeatAttribute")) {
            result = new RepeatAttribute();
        }
        else if (type.equals("harmonica")) {
            result = new Harmonica();
        }
        else if (type.equals("tabModel")) {
            result = new TabModel();
        }
        else {
            result = ElementFactory.create(serializer, object);
        }
        if (result != null) {
            result.deserialize(serializer, object);
            return result;
        }
        throw new IllegalArgumentException("Unhandled object type (" + type + ")");
    }
}
