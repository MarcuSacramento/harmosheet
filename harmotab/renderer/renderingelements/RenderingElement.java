// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.renderingelements;

import harmotab.core.undo.RestoreCommand;
import harmotab.element.Element;

public class RenderingElement extends Element
{
    public RenderingElement(final byte type) {
        super(type);
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        System.err.println("Cannot undo action on a RenderingElement");
        return null;
    }
}
