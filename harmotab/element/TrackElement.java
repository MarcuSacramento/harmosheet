// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

public abstract class TrackElement extends Element
{
    TrackElement(final byte type) {
        super(type);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public abstract String getTrackElementLocalizedName();
}
