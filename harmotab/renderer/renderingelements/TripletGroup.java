// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.renderingelements;

import harmotab.element.Note;
import harmotab.core.Figure;

public class TripletGroup extends NoteGroup
{
    public TripletGroup() {
        super((byte)16);
    }
    
    public Figure getTripletFigure() {
        if (this.size() > 0) {
            return new Figure(((Note)this.get(0).getElement()).getFigure().getType());
        }
        return null;
    }
}
