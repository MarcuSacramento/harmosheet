// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;

public class ElementFactory
{
    public static Element create(final ObjectSerializer serializer, final SerializedObject object) {
        Element element = null;
        final String type = object.getObjectType();
        if (type.equals("bar")) {
            element = new Bar();
        }
        else if (type.equals("key")) {
            element = new Key();
        }
        else if (type.equals("keySignature")) {
            element = new KeySignature();
        }
        else if (type.equals("timeSignature")) {
            element = new TimeSignature();
        }
        else if (type.equals("harmotab")) {
            element = new HarmoTabElement();
        }
        else if (type.equals("accompaniment")) {
            element = new Accompaniment();
        }
        else if (type.equals("chord")) {
            element = new Chord();
        }
        else if (type.equals("note")) {
            element = new Note();
        }
        else if (type.equals("tab")) {
            element = new Tab();
        }
        else if (type.equals("textElement")) {
            element = new TextElement();
        }
        else if (type.equals("tempo")) {
            element = new Tempo();
        }
        else if (type.equals("silence")) {
            element = new Silence();
        }
        else {
            if (!type.equals("lyrics")) {
                throw new IllegalArgumentException("ElementFactory::create: Unhandled element type '" + type + "'");
            }
            element = new Lyrics();
        }
        if (element != null) {
            element.deserialize(serializer, object);
        }
        return element;
    }
}
